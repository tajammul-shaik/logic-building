package exercise.oop.abstractAndInterfaceExample.fleet_logistic;

// ============================================================================
// 1. INTERFACES (Capabilities & Roles)
// ============================================================================

// Interface 1: Can be attached to ANY trackable object (Vehicles, Packages, Humans)
interface GPSDependable {
    String getCoordinates();

    // Java 8+ Default Method: Shared public utility without forcing override
    default void logCurrentLocation() {
        System.out.println("[GPS BROADCAST] Location: " + getCoordinates());
    }
}
