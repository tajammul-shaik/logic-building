package exercise.processingOrder;

// -------------------------------------------------------------------------
// 7. IMMUTABLE CLASS + COPY PATTERN (Better than clone) + METHOD CHAINING
// -------------------------------------------------------------------------
final class CustomerProfile {
    private final String name;
    private final String email;

    // Canonical Constructor
    public CustomerProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // CONSTRUCTOR CHAINING via this(...): Default email if not provided
    public CustomerProfile(String name) {
        this(name, "unknown@store.com");
    }

    // COPY CONSTRUCTOR (The modern, safe alternative to clone())
    public CustomerProfile(CustomerProfile original) {
        this(original.name, original.email); // Deep/safe value copy
    }

    // FLUENT METHOD CHAINING (returns a new instance instead of mutating)
    public CustomerProfile withEmail(String newEmail) {
        return new CustomerProfile(this.name, newEmail);
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}
