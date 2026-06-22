package exercise.design.patterns.creational.singleton;

// 1. The Non-Thread-Safe Singleton
class NonThreadSafeSingleton {
    private static NonThreadSafeSingleton instance;

    private NonThreadSafeSingleton() {
        // Printing the hashcode helps us see if different objects in memory were created
        System.out.println(" ❌ Non-Thread-Safe constructor called! HashCode: " + this.hashCode());
    }

    public static NonThreadSafeSingleton getInstance() {
        if (instance == null) {
            // We simulate a slight delay here. This guarantees that Thread B
            // has time to check (instance == null) before Thread A finishes creating it.
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new NonThreadSafeSingleton();
        }
        return instance;
    }
}