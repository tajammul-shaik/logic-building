package exercise.collections.comparator;

/**
 * Plain data holder - deliberately does NOT implement Comparable.
 * Simulates a type you don't own (e.g. a library/DTO class) where the only option for
 * ordering it is an external Comparator, and where you may need several different orderings
 * depending on the screen/report that's sorting it.
 */
public class Product {
    private final String name;
    private final double price;
    private final int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ", stock=" + stock + ")";
    }
}