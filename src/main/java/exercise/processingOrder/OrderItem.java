package exercise.processingOrder;

public record OrderItem(String name, double price, int quantity) {
    // Compact constructor for validation
    public OrderItem{
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }
}
