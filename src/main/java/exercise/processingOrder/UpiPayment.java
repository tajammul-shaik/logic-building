package exercise.processingOrder;

// -------------------------------------------------------------------------
// 6. CONCRETE CLASS (Child 2): Another permitted child of the sealed class
// -------------------------------------------------------------------------
final class UpiPayment extends PaymentMethod {
    private final String vpaAddress;

    public UpiPayment(String accountHolder, String vpaAddress) {
        super(accountHolder);
        this.vpaAddress = vpaAddress;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Authorizing UPI transfer from " + vpaAddress);
        return true;
    }

    @Override
    public String getProviderDetails() {
        return "Unified Payments Interface";
    }
}
