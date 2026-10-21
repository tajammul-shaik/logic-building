package exercise.collections.comparator;

import java.util.Comparator;

/**
 * Centralizes reusable Comparators so call sites don't repeat lambdas.
 *
 * BEST PRACTICES:
 * - Prefer Comparator.comparing(keyExtractor) / comparingInt / comparingDouble over hand-rolled
 *   (a, b) -> ... lambdas: it reads better, avoids autoboxing, and avoids the overflow/truncation
 *   bugs shown below.
 * - Chain with thenComparing(...) to get deterministic tie-breaking instead of leaving ties to
 *   whatever incidental order the collection produces.
 * - Keep comparators as named constants (like these) instead of inlining the same lambda at
 *   every call site - one place to fix if the ordering rule changes.
 */
public final class ProductComparators {

    private ProductComparators() {
        // utility class - not meant to be instantiated
    }

    public static final Comparator<Product> BY_NAME = Comparator.comparing(Product::getName);

    public static final Comparator<Product> BY_PRICE = Comparator.comparingDouble(Product::getPrice);

    // Cheapest first, then alphabetical for products that share a price.
    public static final Comparator<Product> BY_PRICE_THEN_NAME = BY_PRICE.thenComparing(BY_NAME);

    public static final Comparator<Product> BY_STOCK_DESC =
            Comparator.comparingInt(Product::getStock).reversed();

    /**
     * WHAT TO AVOID: casting a double difference straight to int.
     * `(int) (a.getPrice() - b.getPrice())` truncates any difference smaller than 1.0 to zero.
     * So price 10.9 vs 10.1 -> "equal" (diff 0.8 truncates to 0), 10.1 vs 9.5 -> "equal" (diff
     * 0.6 truncates to 0), but 10.9 vs 9.5 -> "not equal" (diff 1.4 truncates to 1). That's a
     * transitivity violation (A==B, B==C, but A!=C), and once a list is long enough the JDK's
     * merge sort detects it and throws:
     *   IllegalArgumentException: Comparison method violates its general contract!
     * Always use Double.compare()/Integer.compare() or comparingDouble/comparingInt instead.
     * Demonstrated in ComparatorDemo.
     */
    public static final Comparator<Product> BROKEN_TRUNCATING_COMPARATOR =
            (a, b) -> (int) (a.getPrice() - b.getPrice());
}