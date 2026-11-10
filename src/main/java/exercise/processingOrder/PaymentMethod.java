package exercise.processingOrder;

// -------------------------------------------------------------------------
// 4. SEALED CLASS + ABSTRACT CLASS + INHERITANCE:
//    - Sealed: Restricts who can extend it (only CreditCard & UPI allowed)
//    - Abstract: Cannot be instantiated directly; holds shared state & lifecycle
// -------------------------------------------------------------------------
abstract sealed class PaymentMethod implements Payable permits CreditCardPayment, UpiPayment {
    protected final String accountHolder; // Encapsulated shared state

    // CONSTRUCTOR
    protected PaymentMethod(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    // ABSTRACT METHOD: Subclasses must provide concrete code
    public abstract String getProviderDetails();

    // TEMPLATE METHOD: Fixed lifecycle calling concrete + abstract pieces
    public void execute(double amount) {
        System.out.println("\n[Initiating transaction for " + accountHolder + "]");
        System.out.println("Channel: " + getProviderDetails());
        if (pay(amount)) { // Dynamic dispatch: calls subclass's pay()
            printReceipt(amount);
        } else {
            System.out.println("Transaction Failed!");
        }
    }
}