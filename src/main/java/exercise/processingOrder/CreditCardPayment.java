package exercise.processingOrder;

// -------------------------------------------------------------------------
// 5. CONCRETE CLASS (Child 1): Fully implemented, instantiable
// -------------------------------------------------------------------------
final class CreditCardPayment extends PaymentMethod {
    private final String cardNumber;

    // CONSTRUCTOR CHAINING: Using super(...) to initialize parent state first
    public CreditCardPayment(String accountHolder, String cardNumber) {
        super(accountHolder); // Delegating to PaymentMethod constructor
        this.cardNumber = cardNumber; // 'this' disambiguates the field
    }

    // OVERRIDE: Providing runtime implementation of abstract contract
    @Override
    public boolean pay(double amount) {
        System.out.println("Charging card **** " + cardNumber.substring(cardNumber.length() - 4));
        return true;
    }

    // OVERLOAD: Same method name, different parameter signature
    public boolean pay(double amount, double discountPercent) {
        double discounted = amount * (1.0 - (discountPercent / 100.0));
        System.out.println("Applying promo of " + discountPercent + "%");
        return this.pay(discounted);
    }

    @Override
    public String getProviderDetails() {
        return "Visa/Mastercard Gateway";
    }
}
