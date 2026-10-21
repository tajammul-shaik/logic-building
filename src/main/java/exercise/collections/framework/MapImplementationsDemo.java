package exercise.collections.framework;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * MapImplementationsDemo.java
 *
 * Map is a separate hierarchy from Collection (it holds key-value pairs, not single elements),
 * but it's part of the same java.util Collections Framework and follows the same design pattern
 * as Set - in fact HashSet/LinkedHashSet/TreeSet are thin wrappers around HashMap/LinkedHashMap/
 * TreeMap (see SetImplementationsDemo).
 *
 * HOW EACH IS BUILT IN MEMORY:
 * - HashMap holds a `Node<K,V>[] table` (the "bucket array"). A key's hashCode() is spread
 *   (hash ^ (hash >>> 16)) and masked to pick a bucket index. Each bucket is a short linked list
 *   of entries that landed there; since Java 8, a bucket with 8+ colliding entries converts to a
 *   red-black tree (O(log n) worst case instead of O(n)) once the table itself is large enough.
 *   The table length is always a power of two and doubles once size exceeds capacity * loadFactor
 *   (default loadFactor 0.75), rehashing every entry into the new table. Average O(1) get/put.
 * - LinkedHashMap is a HashMap plus a doubly-linked list threading through all entries, giving
 *   predictable iteration order (insertion order by default, or access order if configured) at
 *   the cost of two extra references per entry.
 * - TreeMap is a red-black tree keyed by natural ordering or a Comparator - O(log n) get/put,
 *   always-sorted iteration, and range operations (see NavigableMap below) that hash-based maps
 *   can't offer.
 * - Hashtable (legacy) is essentially HashMap with every method synchronized, and it rejects
 *   null keys/values (HashMap allows one null key and many null values).
 * - ConcurrentHashMap does NOT synchronize the whole map on every call. It uses a table like
 *   HashMap's, but writes are synchronized per-bucket (bin) and reads are largely lock-free, so
 *   many threads can read/write different bins at the same time. It also rejects null keys/
 *   values (a null return from get() must unambiguously mean "absent", which multi-threaded
 *   callers rely on).
 *
 * WHEN TO USE WHAT:
 * - HashMap: default choice for key -> value lookups.
 * - LinkedHashMap: you need predictable iteration order, or you're building an LRU cache (shown
 *   below via removeEldestEntry).
 * - TreeMap: you need sorted keys or range queries (floorKey/ceilingKey/firstKey/subMap).
 * - ConcurrentHashMap: the map is shared across threads. Never share a plain HashMap across
 *   threads without external synchronization - concurrent structural modification can corrupt
 *   its internal structure or livelock, not just throw a checked exception.
 * - Hashtable: legacy, avoid in new code.
 *
 * BEST PRACTICES:
 * - Override equals()/hashCode() together for custom key types (see exercise.collections.
 *   comparable.Employee for the pattern) and never mutate a field used in hashCode() after
 *   using the object as a key (same hazard as SetImplementationsDemo#mutatingAHashKeyLosesTheElement).
 * - Prefer getOrDefault/computeIfAbsent/merge over manual containsKey+get+put - they're both
 *   more concise and (for the concurrent map) atomic.
 * - Pre-size with the expected entry count when known, to avoid repeated resize+rehash.
 */
public class MapImplementationsDemo {

    public static void main(String[] args) throws InterruptedException {
        computeIdiomsInsteadOfManualLookups();
        linkedHashMapAsAnLruCache();
        treeMapRangeQueries();
        concurrentHashMapUnderConcurrentWrites();
    }

    private static void computeIdiomsInsteadOfManualLookups() {
        System.out.println("--- 1. getOrDefault/computeIfAbsent/merge instead of manual containsKey+get+put ---");
        Map<String, Integer> wordCounts = new java.util.HashMap<>();
        String[] words = {"java", "streams", "java", "collections", "java", "streams"};

        for (String word : words) {
            // BEST PRACTICE: single atomic-looking update instead of
            // `if (map.containsKey(word)) map.put(word, map.get(word)+1); else map.put(word,1);`
            wordCounts.merge(word, 1, Integer::sum);
        }
        System.out.println("Word counts: " + wordCounts);
    }

    // A bounded cache that evicts the Least Recently Used entry once it grows past capacity.
    private static void linkedHashMapAsAnLruCache() {
        System.out.println("\n--- 2. LinkedHashMap in access-order mode = a ready-made LRU cache ---");
        final int capacity = 3;
        Map<String, String> lruCache = new LinkedHashMap<>(capacity, 0.75f, true) { // true = access order
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > capacity; // evict the least-recently-used entry once over capacity
            }
        };

        lruCache.put("A", "1");
        lruCache.put("B", "2");
        lruCache.put("C", "3");
        lruCache.get("A"); // touching A marks it as most-recently-used, so B becomes the eldest
        lruCache.put("D", "4"); // over capacity -> evicts B, the least-recently-used entry

        System.out.println("After inserting A,B,C, re-reading A, then inserting D: " + lruCache);
        System.out.println("(B was evicted - it was the least recently used, not the first inserted)");
    }

    private static void treeMapRangeQueries() {
        System.out.println("\n--- 3. TreeMap range/navigation operations HashMap can't offer ---");
        NavigableMap<Integer, String> scoreToGrade = new TreeMap<>(Map.of(
                90, "A", 80, "B", 70, "C", 60, "D"
        ));
        int studentScore = 74;
        System.out.println("Score->grade table: " + scoreToGrade);
        System.out.println("floorEntry(" + studentScore + ") [nearest grade at or below the score]="
                + scoreToGrade.floorEntry(studentScore));
    }

    // Runs many threads hammering the same map to show ConcurrentHashMap's atomic merge holds up.
    private static void concurrentHashMapUnderConcurrentWrites() throws InterruptedException {
        System.out.println("\n--- 4. ConcurrentHashMap: atomic per-key updates under real concurrency ---");
        Map<String, Integer> hits = new ConcurrentHashMap<>();
        int threadCount = 8;
        int incrementsPerThread = 10_000;

        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        for (int t = 0; t < threadCount; t++) {
            pool.submit(() -> {
                for (int i = 0; i < incrementsPerThread; i++) {
                    hits.merge("shared-counter", 1, Integer::sum); // atomic read-modify-write
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        int expected = threadCount * incrementsPerThread;
        System.out.println("Expected shared-counter=" + expected + ", actual=" + hits.get("shared-counter")
                + " (matches: merge() is atomic per key - a plain HashMap here could lose updates or corrupt state).");
    }
}