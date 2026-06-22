package exercise.design.patterns.creational.singleton;

// 3. The Main Runner Class
public class SingletonTest {

    public static void main(String[] args) {
        System.out.println("--- TESTING NON-THREAD-SAFE SINGLETON ---");
        System.out.println("Expected: Multiple threads will bypass the null check and create multiple instances.\n");

        // Create 3 threads that try to get the Non-Thread-Safe instance simultaneously
        Thread t1 = new Thread(NonThreadSafeSingleton::getInstance);
        Thread t2 = new Thread(NonThreadSafeSingleton::getInstance);
        Thread t3 = new Thread(NonThreadSafeSingleton::getInstance);

        t1.start();
        t2.start();
        t3.start();

        // Wait for the first test to finish before starting the second one
        try {
            t1.join(); t2.join(); t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- TESTING THREAD-SAFE SINGLETON ---");
        System.out.println("Expected: Despite multiple threads trying, only ONE instance will be created.\n");

        // Create 3 threads that try to get the Thread-Safe instance simultaneously
        Thread t4 = new Thread(ThreadSafeSingleton::getInstance);
        Thread t5 = new Thread(ThreadSafeSingleton::getInstance);
        Thread t6 = new Thread(ThreadSafeSingleton::getInstance);

        t4.start();
        t5.start();
        t6.start();
    }
}
