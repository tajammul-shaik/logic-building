package exercise.collections.framework;

/**
 * CollectionFrameworkGuide.java
 *
 * Not a demo of any one class - a map of the whole java.util Collections Framework: the
 * interface hierarchy, and a decision table for "which implementation do I reach for".
 * The concrete implementations are demonstrated (with runnable proof of their behavior) in
 * the sibling classes: ListImplementationsDemo, SetImplementationsDemo,
 * QueueAndDequeImplementationsDemo, MapImplementationsDemo.
 */
public class CollectionFrameworkGuide {

    public static void main(String[] args) {
        printHierarchy();
        printDecisionTable();
    }

    private static void printHierarchy() {
        System.out.println("=== java.util Collections Framework - interface hierarchy ===");
        System.out.println("""
                Iterable
                  \\-- Collection                            Map (separate hierarchy - NOT a Collection)
                        |-- List   (ordered, duplicates OK)    |-- HashMap
                        |     |-- ArrayList                    |     \\-- LinkedHashMap
                        |     |-- LinkedList (also a Deque)    |-- TreeMap (SortedMap / NavigableMap)
                        |     \\-- Vector (legacy)              |-- Hashtable (legacy)
                        |           \\-- Stack (legacy)          \\-- ConcurrentHashMap (concurrent)
                        |
                        |-- Set    (no duplicates)
                        |     |-- HashSet
                        |     |     \\-- LinkedHashSet
                        |     |-- TreeSet (SortedSet / NavigableSet)
                        |     \\-- EnumSet (abstract, enum-only)
                        |
                        \\-- Queue  (FIFO / priority processing)
                              |-- PriorityQueue
                              |-- LinkedList (also a List)
                              \\-- Deque (double-ended queue)
                                    |-- ArrayDeque
                                    \\-- LinkedList
                """);
    }

    private static void printDecisionTable() {
        System.out.println("=== Quick decision guide ===");
        System.out.println("""
                Need...                                              -> Reach for
                --------------------------------------------------------------------------
                Indexed list, default choice                          -> ArrayList
                Thread-safe read-mostly list (listeners, etc.)        -> CopyOnWriteArrayList
                Unique elements, no ordering need                     -> HashSet
                Unique elements, preserve insertion order             -> LinkedHashSet
                Unique elements, need sorted iteration / range query  -> TreeSet
                Unique elements of a single enum type                 -> EnumSet
                Stack or queue behavior                               -> ArrayDeque (never Stack/Vector)
                "give me the smallest/largest next" processing        -> PriorityQueue
                Key -> value lookup, default choice                   -> HashMap
                Key -> value, preserve insertion order / build an LRU -> LinkedHashMap
                Key -> value, need sorted keys / range query          -> TreeMap
                Key -> value under concurrent access                  -> ConcurrentHashMap
                --------------------------------------------------------------------------
                AVOID unless you have a specific legacy reason: Vector, Stack, Hashtable -
                every method on them is synchronized (slow even single-threaded) and they
                predate the Collections Framework's design conventions (e.g. Hashtable
                rejects null keys/values where HashMap allows them).
                """);
    }
}