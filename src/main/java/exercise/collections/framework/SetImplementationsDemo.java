package exercise.collections.framework;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * SetImplementationsDemo.java
 *
 * Set: a Collection with no duplicates, as decided by equals() (or compareTo() for TreeSet -
 * see exercise.collections.comparable.ComparableDemo for what goes wrong when those two disagree).
 *
 * HOW EACH IS BUILT IN MEMORY:
 * - HashSet is a thin wrapper around a HashMap<E, Object> where every element is stored as a
 *   key mapped to a shared dummy value. So its memory layout and cost are exactly HashMap's:
 *   an array of buckets, each holding a short chain (or, since Java 8, a small red-black tree
 *   once a single bucket gets 8+ colliding entries) of elements that hashed to that bucket.
 *   O(1) average add/remove/contains; iteration order is whatever the bucket layout happens
 *   to be - unspecified and not to be relied on.
 * - LinkedHashSet is a HashSet with an extra doubly-linked list threaded through all entries in
 *   insertion order. Same O(1) average operations, plus predictable iteration order, at the
 *   cost of two extra references per entry.
 * - TreeSet is backed by a TreeMap, i.e. a red-black tree (a self-balancing binary search tree).
 *   O(log n) add/remove/contains, but iteration is always in sorted order, and it exposes range
 *   operations (first/last/headSet/tailSet/floor/ceiling) that hash-based sets can't offer.
 * - EnumSet is never actually a hash table: for enums with <= 64 constants it's a single `long`
 *   used as a bit vector (RegularEnumSet); for larger enums it's a `long[]` (JumboEnumSet). Each
 *   element is one bit, so add/remove/contains are single bitwise operations and the whole set
 *   fits in a handful of bytes.
 *
 * WHEN TO USE WHAT:
 * - HashSet: default choice when you just need uniqueness and don't care about order.
 * - LinkedHashSet: same as HashSet, but you need iteration/print order to match insertion order
 *   (e.g. de-duplicating a list while preserving its original order).
 * - TreeSet: you need the elements sorted, or need "closest value" / range queries.
 * - EnumSet: ALWAYS prefer this over HashSet<SomeEnum> - it's both faster and far smaller.
 *
 * BEST PRACTICES:
 * - Always override equals()/hashCode() together (and consistently) for objects placed in a
 *   HashSet/LinkedHashSet - see exercise.collections.comparable.Employee for the pattern.
 * - AVOID mutating a field that's part of an object's hashCode() after it's already been added
 *   to a HashSet/LinkedHashSet: the element gets "lost" in the wrong bucket and contains()/
 *   remove() for it will silently return false, demonstrated below.
 * - TreeSet (and TreeMap) throw NullPointerException on a null element/key under natural
 *   ordering, since there is no "compareTo(null)". Don't assume every Set accepts null the way
 *   HashSet does.
 */
public class SetImplementationsDemo {

    public static void main(String[] args) {
        orderingComparison();
        treeSetRangeQueries();
        enumSetIsABitVector();
        mutatingAHashKeyLosesTheElement();
    }

    private static void orderingComparison() {
        System.out.println("--- 1. Iteration order: HashSet (unspecified) vs LinkedHashSet vs TreeSet ---");
        Set<String> input = Set.of("date", "banana", "apple", "cherry");

        Set<String> linkedHashSet = new LinkedHashSet<>();
        Set<String> treeSet = new TreeSet<>();
        // Insert in a fixed order so LinkedHashSet's guarantee is observable.
        for (String s : new String[]{"date", "banana", "apple", "cherry"}) {
            linkedHashSet.add(s);
            treeSet.add(s);
        }

        System.out.println("HashSet (order not guaranteed, AVOID relying on it): " + new HashSet<>(input));
        System.out.println("LinkedHashSet (insertion order): " + linkedHashSet);
        System.out.println("TreeSet (sorted/natural order): " + treeSet);
    }

    private static void treeSetRangeQueries() {
        System.out.println("\n--- 2. TreeSet range/navigation operations HashSet can't offer ---");
        NavigableSet<Integer> scores = new TreeSet<>(Set.of(42, 7, 15, 23, 4, 99));
        System.out.println("All scores sorted: " + scores);
        System.out.println("first()=" + scores.first() + ", last()=" + scores.last());
        System.out.println("floor(20) [largest <= 20]=" + scores.floor(20));
        System.out.println("ceiling(20) [smallest >= 20]=" + scores.ceiling(20));
        System.out.println("headSet(23) [< 23]=" + scores.headSet(23));
        System.out.println("tailSet(23) [>= 23]=" + scores.tailSet(23));
    }

    private enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

    private static void enumSetIsABitVector() {
        System.out.println("\n--- 3. EnumSet: a bit vector, not a hash table ---");
        Set<Day> weekend = EnumSet.of(Day.SAT, Day.SUN);
        Set<Day> weekdays = EnumSet.complementOf((EnumSet<Day>) weekend);
        System.out.println("weekend=" + weekend + ", weekdays=" + weekdays);
        System.out.println("Iterates in enum-declaration order, not insertion or hash order: "
                + EnumSet.of(Day.FRI, Day.MON, Day.WED));
    }

    // A deliberately mutable "bad" key type to demonstrate the hazard.
    private static class MutableKey {
        int id;

        MutableKey(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof MutableKey && ((MutableKey) o).id == id;
        }

        @Override
        public int hashCode() {
            return id; // AVOID basing hashCode on a field that can change after insertion
        }

        @Override
        public String toString() {
            return "MutableKey(" + id + ")";
        }
    }

    private static void mutatingAHashKeyLosesTheElement() {
        System.out.println("\n--- 4. AVOID: mutating a field used in hashCode() after insertion ---");
        Set<MutableKey> set = new HashSet<>();
        MutableKey key = new MutableKey(1);
        set.add(key);
        System.out.println("Before mutation, contains(key)=" + set.contains(key));

        key.id = 2; // the object is now sitting in the bucket for hash 1, but reports hash 2
        System.out.println("After mutating key.id, contains(key)=" + set.contains(key)
                + " (lost! it's stuck in the old bucket) - set still holds it internally: " + set);
    }
}