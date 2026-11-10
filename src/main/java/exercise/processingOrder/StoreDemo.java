package exercise.processingOrder;

public class StoreDemo {
    public static void main(String[] args) {

        // CLASS vs OBJECT
        // CustomerProfile is the CLASS (the code/blueprint)
        // alex is the OBJECT (actual allocated memory in the heap)
        CustomerProfile alex = new CustomerProfile("Alex Hunter");

        // METHOD CHAINING & IMMUTABILITY
        // 'alex' is never changed; 'updatedAlex' is a brand-new object
        CustomerProfile updatedAlex = alex.withEmail("alex.h@gmail.com");

        // COPY CONSTRUCTOR (replacing clone)
        CustomerProfile duplicateAlex = new CustomerProfile(updatedAlex);

        // RECORD instantiation (simple, compact data container)
        OrderItem headphones = new OrderItem("Noise Cancelling Headphones", 150.00, 2);

        // ENUM usage with behavior
        double totalInEUR = headphones.price() * headphones.quantity();
        double totalInUSD = Currency.EUR.toUSD(totalInEUR);

        System.out.println("Purchased: " + headphones.name());
        System.out.println("Total converted to USD: $" + Math.round(totalInUSD * 100.0) / 100.0);

        // -----------------------------------------------------------------
        // POLYMORPHISM & OVERRIDE IN ACTION
        // Treating different concrete objects uniformly through their parent type
        // -----------------------------------------------------------------
        PaymentMethod payment1 = new CreditCardPayment(updatedAlex.getName(), "4111222233339876");
        PaymentMethod payment2 = new UpiPayment(updatedAlex.getName(), "alex@okhdfcbank");

        // Dynamic dispatch: The runtime calls the correct pay() version for each
        payment1.execute(totalInUSD);
        payment2.execute(totalInUSD);

        // -----------------------------------------------------------------
        // OVERLOAD IN ACTION
        // Resolved at compile-time based on the method signature
        // -----------------------------------------------------------------
        CreditCardPayment specificCard = (CreditCardPayment) payment1;
        specificCard.pay(totalInUSD, 10.0); // Calls the 2-argument overloaded pay()
    }
}
