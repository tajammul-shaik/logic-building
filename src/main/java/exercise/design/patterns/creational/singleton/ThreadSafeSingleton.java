package exercise.design.patterns.creational.singleton;

// 2. The Thread-Safe Singleton (Double-Checked Locking)
class ThreadSafeSingleton {
    private static volatile ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {
        System.out.println(" ✅ Thread-Safe constructor called! HashCode: " + this.hashCode());
    }

    public static void getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    // Even with the same delay, the synchronized lock ensures
                    // no other thread can enter this block while we wait.
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = new ThreadSafeSingleton();
                }
            }
        }
    }
}
