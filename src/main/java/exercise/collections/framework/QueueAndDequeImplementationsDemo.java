package exercise.collections.framework;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * QueueAndDequeImplementationsDemo.java
 *
 * Queue: a Collection designed for holding elements for processing, typically FIFO.
 * Deque ("deck"): a Queue that supports insertion/removal at BOTH ends, so it can act as a
 * queue, a stack, or both.
 *
 * HOW EACH IS BUILT IN MEMORY:
 * - ArrayDeque wraps a single resizable array used as a CIRCULAR buffer, with head/tail index
 *   pointers that wrap around the ends of the array. No per-element node overhead, excellent
 *   cache locality, and it resizes (doubles) the same way ArrayList does. It does not allow
 *   null elements (null is reserved internally to mean "empty slot").
 * - LinkedList (as a Deque) is the same doubly-linked Node chain described in
 *   ListImplementationsDemo - O(1) at both ends like ArrayDeque, but with node overhead and
 *   worse cache locality. Unlike ArrayDeque, it does allow null elements.
 * - PriorityQueue is backed by a single array organized as a BINARY HEAP, not FIFO order at
 *   all: the array is kept in "heap order" (a parent is always <= both its children, by natural
 *   ordering or a supplied Comparator) so the smallest element is always at index 0. offer() is
 *   O(log n) ("sift up"), poll() is O(log n) ("sift down"), peek() is O(1). It does not allow
 *   null elements, and iterating it does NOT visit elements in sorted order - only repeated
 *   poll() does.
 * - Stack (legacy, extends Vector) and the synchronized methods it inherits are avoided in
 *   modern code in favor of ArrayDeque, which is both faster and unsynchronized by default.
 *
 * WHEN TO USE WHAT:
 * - ArrayDeque: default choice for a stack (push/pop) or a queue (offer/poll). Faster than both
 *   LinkedList and the legacy Stack for these roles.
 * - LinkedList as a Deque: only if you specifically need null elements in the queue, or you're
 *   already using it as a List elsewhere in the same code path.
 * - PriorityQueue: whenever "process the smallest/largest/most-urgent item next" is the actual
 *   requirement (task scheduling, Dijkstra's algorithm, merging k sorted streams, etc.) - not a
 *   substitute for a general Queue.
 *
 * BEST PRACTICES:
 * - Use offer()/poll()/peek() (return a sentinel/null on empty) rather than add()/remove()/
 *   element() (throw on empty) unless an empty queue really is exceptional in your code path.
 * - AVOID iterating a PriorityQueue expecting sorted output - drain it with poll() instead.
 * - AVOID Stack/Vector for new code - use ArrayDeque.
 */
public class QueueAndDequeImplementationsDemo {

    public static void main(String[] args) {
        priorityQueueOrdersByPriorityNotInsertion();
        arrayDequeAsStackAndAsQueue();
    }

    private static void priorityQueueOrdersByPriorityNotInsertion() {
        System.out.println("--- 1. PriorityQueue: poll() order is by priority, not insertion order ---");
        Queue<Integer> tasks = new PriorityQueue<>();
        for (int priority : new int[]{5, 1, 4, 2, 3}) {
            tasks.offer(priority);
        }
        System.out.println("Insertion order was: 5, 1, 4, 2, 3");
        System.out.print("poll() order is:     ");
        while (!tasks.isEmpty()) {
            System.out.print(tasks.poll() + (tasks.isEmpty() ? "" : ", "));
        }
        System.out.println();
    }

    private static void arrayDequeAsStackAndAsQueue() {
        System.out.println("\n--- 2. ArrayDeque used as a stack (LIFO) and as a queue (FIFO) ---");
        Deque<String> stack = new ArrayDeque<>();
        stack.push("first-pushed");
        stack.push("second-pushed");
        stack.push("third-pushed");
        System.out.println("Stack pop() order (LIFO): " + stack.pop() + ", " + stack.pop() + ", " + stack.pop());

        Deque<String> queue = new ArrayDeque<>();
        queue.offer("first-in");
        queue.offer("second-in");
        queue.offer("third-in");
        System.out.println("Queue poll() order (FIFO): " + queue.poll() + ", " + queue.poll() + ", " + queue.poll());

        Deque<String> linkedListAsDeque = new LinkedList<>();
        linkedListAsDeque.push("a");
        linkedListAsDeque.push("b");
        System.out.println("\nContrast: LinkedList supports the same Deque operations, just with node overhead: "
                + linkedListAsDeque);
    }
}