package exercise.processingOrder;

public interface Payable {

    // Abstract contract method
    boolean pay(double amount);

    //Default method(Shared utility logic without breaking implementers)
    default void printReceipt(double amount){
        System.out.println("Receipt: Processed payment of $: "+amount);
    }
}
