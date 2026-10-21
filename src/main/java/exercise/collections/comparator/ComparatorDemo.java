package exercise.collections.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static exercise.collections.comparator.ProductComparators.*;

/**
 * ComparatorDemo.java
 *
 * Shows external ordering (Comparator) in action: reusable comparators, chaining with
 * thenComparing, reversing, null-safe ordering, and the classic "contract violation" bug.
 */
public class ComparatorDemo {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(List.of(
                new Product("Widget", 19.99, 100),
                new Product("Gadget", 19.99, 40),
                new Product("Anvil", 9.50, 15),
                new Product("Sprocket", 30.00, 200)
        ));

        System.out.println("--- 1. Comparator.comparing + reusable constants ---");
        products.sort(BY_NAME);
        System.out.println("By name: " + products);

        products.sort(BY_PRICE);
        System.out.println("By price: " + products);

        System.out.println("\n--- 2. Chaining with thenComparing for deterministic tie-breaks ---");
        products.sort(BY_PRICE_THEN_NAME);
        System.out.println("By price then name: " + products);

        System.out.println("\n--- 3. reversed() ---");
        products.sort(BY_STOCK_DESC);
        System.out.println("By stock, descending: " + products);

        System.out.println("\n--- 4. Null-safe ordering ---");
        List<String> withNulls = new ArrayList<>(List.of("banana", "apple"));
        withNulls.add(null);
        withNulls.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        System.out.println("AVOID Comparator.naturalOrder() alone here - it throws on null.");
        System.out.println("nullsFirst(naturalOrder()) instead: " + withNulls);

        System.out.println("\n--- 5. AVOID: a comparator that violates the general contract ---");
        System.out.println("BROKEN_TRUNCATING_COMPARATOR truncates a double diff to int, which is");
        System.out.println("not transitive. On a long enough list, TimSort detects this and throws.");
        Random seeded = new Random(0); // fixed seed so this reliably reproduces the failure
        List<Product> manyProducts = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            manyProducts.add(new Product("P" + i, seeded.nextDouble() * 2, i));
        }
        try {
            manyProducts.sort(BROKEN_TRUNCATING_COMPARATOR);
            System.out.println("Did not throw this run - the bug is still there, just data-dependent.");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected: " + e.getMessage());
        }
    }
}