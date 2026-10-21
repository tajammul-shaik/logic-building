package exercise.collections.framework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ListImplementationsDemo.java
 *
 * List: an ordered Collection that allows duplicates and index-based access (get/set/add at
 * a position).
 *
 * HOW EACH IS BUILT IN MEMORY:
 * - ArrayList wraps a single Object[] array. Elements sit contiguously, so get(index) is O(1)
 *   pointer arithmetic. When the array fills up, add() allocates a NEW, bigger array (grows by
 *   ~1.5x its current capacity) and copies every existing reference into it - an O(n) copy that
 *   happens occasionally, not on every add, so add() is amortized O(1).
 * - LinkedList wraps a chain of separately heap-allocated Node objects, each holding the element
 *   plus `prev`/`next` references. Nothing is copied on insert/remove, but each node costs
 *   noticeably more memory than one ArrayList slot (object header + 2 references per element),
 *   and there is no random access: get(index) must walk the chain from whichever end is closer,
 *   so it's O(n) - proven below.
 * - Vector is essentially ArrayList with every method marked `synchronized`; Stack extends
 *   Vector. Both predate java.util.concurrent and pay locking overhead on every call even in a
 *   single-threaded program.
 * - CopyOnWriteArrayList also wraps an Object[], but every mutating call (add/remove/set)
 *   allocates a fresh copy of the entire array and swaps it in atomically. Readers/iterators
 *   see a private, unchanging snapshot, so they never throw ConcurrentModificationException and
 *   never need external locking - at the cost of an O(n) copy per write.
 *
 * WHEN TO USE WHAT:
 * - ArrayList: default choice for almost everything.
 * - LinkedList: only when you need O(1) insert/remove at both ends AND never do indexed access -
 *   and even then, ArrayDeque (see QueueAndDequeImplementationsDemo) usually wins because a
 *   circular array has better cache locality and less per-element overhead than linked nodes.
 * - CopyOnWriteArrayList: read-heavy, write-rare, concurrently accessed lists (e.g. a list of
 *   event listeners). AVOID for write-heavy workloads - each write is an O(n) full copy.
 * - Vector / Stack: avoid. Use ArrayList/ArrayDeque, or java.util.concurrent types if you
 *   genuinely need thread safety.
 *
 * BEST PRACTICES:
 * - Pre-size when the count is roughly known: `new ArrayList<>(expectedSize)` avoids repeated
 *   grow-and-copy cycles.
 * - Declare variables/fields/parameters as `List<T>`, not `ArrayList<T>`, so the implementation
 *   can change later without touching every call site.
 * - AVOID calling get(i) in a loop on a LinkedList - each call is O(n), so the loop is an
 *   accidental O(n^2) algorithm. Iterate with a for-each/Iterator instead, which walks the chain
 *   once.
 */
public class ListImplementationsDemo {

    public static void main(String[] args) {
        arrayListVsLinkedListRandomAccess();
        copyOnWriteIsSafeToMutateWhileIterating();
    }

    // Proves the O(1) vs O(n) random-access claim above with a real timing measurement.
    private static void arrayListVsLinkedListRandomAccess() {
        System.out.println("--- 1. Random access: ArrayList O(1)/call vs LinkedList O(n)/call ---");
        int elementCount = 20_000;
        List<Integer> arrayList = new ArrayList<>(elementCount); // pre-sized: no resize copies
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < elementCount; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        long start = System.nanoTime();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i);
        }
        long arrayListNanos = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < linkedList.size(); i++) {
            linkedList.get(i); // AVOID in real code - shown here only to prove the point
        }
        long linkedListNanos = System.nanoTime() - start;

        System.out.println("Indexed loop over " + elementCount + " elements - ArrayList: "
                + (arrayListNanos / 1_000_000) + "ms, LinkedList: " + (linkedListNanos / 1_000_000) + "ms");
        System.out.println("LinkedList is dramatically slower: get(i) walks the node chain from an end, every call.");
    }

    private static void copyOnWriteIsSafeToMutateWhileIterating() {
        System.out.println("\n--- 2. CopyOnWriteArrayList: safe to mutate during iteration ---");
        List<String> listeners = new CopyOnWriteArrayList<>(List.of("listenerA", "listenerB"));

        // Contrast with CollectionBestPractices#modifyingWhileIterating, where doing this to a
        // plain ArrayList throws ConcurrentModificationException.
        for (String listener : listeners) {
            if (listener.equals("listenerA")) {
                listeners.add("addedDuringIteration");
            }
        }
        System.out.println("After iteration (no exception): " + listeners);
        System.out.println("AVOID for write-heavy workloads - every single write copies the ENTIRE backing array.");
    }
}
