package exercise.oop.abstractAndInterfaceExample.fleet_logistic;

// Interface 2: Autonomous navigation capability
interface AutonomousCapable {
    void navigateToDestination(String destination);

    // Java 8+ Default method leveraging Java 9+ Private interface methods
    default void executeAutonomousMission(String destination) {
        performPreFlightSafetyCheck();
        System.out.println("[AUTONOMOUS NAV] Route locked. Proceeding to: " + destination);
        navigateToDestination(destination);
    }

    // Java 9+ Private method: Internal helper encapsulated inside the interface
    private void performPreFlightSafetyCheck() {
        System.out.println("[SAFETY] Calibrating LiDAR and radar sensors...");
    }
}
