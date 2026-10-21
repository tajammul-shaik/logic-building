package exercise.collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CollectionBestPractices.java
 *
 * A tour of common java.util.Collection pitfalls, paired with the idiomatic fix for each.
 * Comparable/Comparator specific pitfalls live in the sibling `comparable` and `comparator`
 * packages - this file is about the collections themselves (List/Set/Map behavior).
 */
public class CollectionBestPractices {

    public static void main(String[] args) {
        modifyingWhileIterating();
        programToInterfaceNotImplementation();
        orderingGuarantees();
        immutableCollections();
        nullHandling();
        threadSafety();
    }

    // --- 1. AVOID: mutating a list through its own remove() while for-each iterates it ---
    private static void modifyingWhileIterating() {
        System.out.println("--- 1. Modifying a list while iterating ---");
        List<String> names = new ArrayList<>(List.of("Alice", "Bob", "Charlie", "Dave"));

        try {
            for (String name : names) {
                if (name.startsWith("B")) {
                    names.remove(name); // AVOID: throws ConcurrentModificationException
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Caught expected " + e.getClass().getSimpleName()
                    + " - never mutate a list from inside a for-each over it.");
        }

        // BEST PRACTICE: use removeIf(...), or an explicit Iterator and call iterator.remove()
        names.removeIf(name -> name.startsWith("B"));
        System.out.println("After removeIf: " + names);
    }

    // --- 2. BEST PRACTICE: declare fields/params/returns as the interface, not the impl ---
    private static void programToInterfaceNotImplementation() {
        System.out.println("\n--- 2. Program to the interface, not the implementation ---");
        // GOOD: `List<String>` on the left lets ArrayList be swapped for LinkedList later
        // without touching any code that consumes `good`.
        List<String> good = new ArrayList<>();
        // AVOID: `ArrayList<String> bad = new ArrayList<>();` leaks the concrete type into
        // every signature that touches `bad`, making that swap a breaking change.
        good.add("example");
        System.out.println("Declared as List<String>, backed by ArrayList: " + good);
    }

    // --- 3. Ordering guarantees differ by implementation - don't assume one ---
    private static void orderingGuarantees() {
        System.out.println("\n--- 3. Ordering guarantees ---");

        Set<String> hashSet = new HashSet<>(List.of("banana", "apple", "cherry"));
        Set<String> linkedHashSet = new LinkedHashSet<>(List.of("banana", "apple", "cherry"));
        Set<String> treeSet = new TreeSet<>(List.of("banana", "apple", "cherry"));

        System.out.println("HashSet (no guaranteed order - AVOID relying on it): " + hashSet);
        System.out.println("LinkedHashSet (insertion order): " + linkedHashSet);
        System.out.println("TreeSet (sorted/natural order): " + treeSet);
    }

    // --- 4. BEST PRACTICE: prefer immutable collections for data that shouldn't change ---
    private static void immutableCollections() {
        System.out.println("\n--- 4. Immutable collections ---");
        List<String> immutable = List.of("a", "b", "c");
        try {
            immutable.add("d"); // AVOID: throws UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("List.of(...) is immutable - caught " + e.getClass().getSimpleName());
        }
    }

    // --- 5. Null handling differs silently between collection implementations ---
    private static void nullHandling() {
        System.out.println("\n--- 5. Null handling ---");
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put(null, "allowed in HashMap");
        System.out.println("HashMap allows a null key: " + hashMap.get(null));

        try {
            Map<String, String> treeMap = new TreeMap<>();
            treeMap.put(null, "boom"); // AVOID: TreeMap has no ordering for null -> NPE
        } catch (NullPointerException e) {
            System.out.println("TreeMap rejects a null key with NPE - don't assume every Map behaves like HashMap.");
        }
    }

    // --- 6. AVOID: sharing a plain HashMap/ArrayList across threads without synchronization ---
    private static void threadSafety() {
        System.out.println("\n--- 6. Thread safety ---");
        System.out.println("Plain HashMap/ArrayList are NOT thread-safe: concurrent writers can corrupt");
        System.out.println("internal state or spin forever. Use ConcurrentHashMap / CopyOnWriteArrayList,");
        System.out.println("or Collections.synchronizedMap(...) and synchronize on it externally when iterating.");
        Map<String, Integer> safeMap = new ConcurrentHashMap<>();
        safeMap.put("counter", 1);
        System.out.println("Example: " + safeMap.getClass().getSimpleName() + " " + safeMap);
    }
}