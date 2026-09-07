package exercise.oop;


// ==========================================
// 1. ABSTRACTION
// ==========================================
// An abstract class defines a template for bank accounts.
// It cannot be instantiated directly; it forces subclasses to implement specific behavior.
abstract class BankAccount {

    // ==========================================
    // 2. ENCAPSULATION
    // ==========================================
    // Fields are marked 'private' so external code cannot tamper with them directly.
    private String accountNumber;
    private String accountHolderName;
    protected double balance; // 'protected' allows direct access in subclasses

    // Constructor to initialize state
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        // Validate initial balance before assigning
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;
            System.out.println("Warning: Initial balance cannot be negative. Set to 0.0");
        }
    }

    // Public Getters (Controlled Read Access)
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // Encapsulated behavior: Deposit method
    // ==========================================
    // 3. POLYMORPHISM (Compile-time / Overloading)
    // ==========================================
    // Method Overloading: Same method name 'deposit', different parameter lists.
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(accountHolderName + " deposited $" + amount + ". New Balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Overloaded deposit method accepting a description note
    public void deposit(double amount, String note) {
        System.out.println("Note: " + note);
        deposit(amount); // Reuses the primary deposit logic
    }

    // Abstract method (Abstraction): Every account MUST implement its own withdrawal rules
    public abstract void withdraw(double amount);

    // Common non-abstract method shared by all accounts
    public void displayAccountInfo() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Holder: " + accountHolderName);
        System.out.println("Account #: " + accountNumber);
        System.out.println("Balance: $" + balance);
    }
}

// Interface providing an optional capability (Abstraction)
interface InterestBearing {
    void applyInterest();
}


// ==========================================
// 4. INHERITANCE & RUNTIME POLYMORPHISM
// ==========================================

// SavingsAccount extends BankAccount (Inheritance)
// It also implements InterestBearing
class SavingsAccount extends BankAccount implements InterestBearing {
    private double interestRate; // Annual percentage (e.g., 0.05 for 5%)

    public SavingsAccount(String accountNumber, String accountHolderName, double initialBalance, double interestRate) {
        // 'super' calls the constructor of the parent class (BankAccount)
        super(accountNumber, accountHolderName, initialBalance);
        this.interestRate = interestRate;
    }

    // Polymorphism (Runtime / Method Overriding)
    // Overrides the abstract withdraw method with Savings account specific rules (no overdraft allowed)
    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(getAccountHolderName() + " withdrew $" + amount + ". Remaining Balance: $" + balance);
        } else {
            System.out.println("Withdrawal failed for " + getAccountHolderName() + ": Insufficient funds!");
        }
    }

    // Implementing interface method
    @Override
    public void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest of $" + interest + " applied to " + getAccountHolderName() + "'s account. New Balance: $" + balance);
    }
}

// CheckingAccount extends BankAccount (Inheritance)
class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolderName, double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolderName, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    // Polymorphism (Runtime / Method Overriding)
    // Checking accounts allow balance to go negative down to the overdraft limit
    @Override
    public void withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= -overdraftLimit) {
            balance -= amount;
            System.out.println(getAccountHolderName() + " withdrew $" + amount + ". Current Balance: $" + balance);
        } else {
            System.out.println("Withdrawal failed for " + getAccountHolderName() + ": Exceeds overdraft limit!");
        }
    }
}


// ==========================================
// MAIN DRIVER CLASS
// ==========================================
public class OOPConceptBankExample {
    public static void main(String[] args) {

        // Creating concrete objects using Inheritance and Constructors
        SavingsAccount aliceAccount = new SavingsAccount("SA-101", "Alice", 1000.0, 0.04);
        CheckingAccount bobAccount = new CheckingAccount("CA-202", "Bob", 300.0, 200.0);

        // ----------------------------------------------------
        // Demonstrating Encapsulation & Overloading
        // ----------------------------------------------------
        aliceAccount.displayAccountInfo();
        aliceAccount.deposit(500.0); // Simple deposit
        aliceAccount.deposit(200.0, "Birthday gift"); // Overloaded deposit with note

        // Direct field access is blocked: aliceAccount.balance = 999999; -> COMPILE ERROR!

        // ----------------------------------------------------
        // Demonstrating Runtime Polymorphism
        // ----------------------------------------------------
        // Polymorphic Array: Treating child objects via their abstract parent reference
        BankAccount[] accounts = { aliceAccount, bobAccount };

        System.out.println("\n==========================================");
        System.out.println("PROCESSING WITHDRAWALS (POLYMORPHISM)");
        System.out.println("==========================================");

        // At runtime, the JVM calls the specific withdraw() implementation for each account type
        for (BankAccount account : accounts) {
            account.withdraw(400.0);
        }

        // ----------------------------------------------------
        // Interface Capabilities
        // ----------------------------------------------------
        System.out.println("\n==========================================");
        System.out.println("APPLYING INTEREST");
        System.out.println("==========================================");
        aliceAccount.applyInterest();
    }
}
