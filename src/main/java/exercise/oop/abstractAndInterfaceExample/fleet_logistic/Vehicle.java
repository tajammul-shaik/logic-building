package exercise.oop.abstractAndInterfaceExample.fleet_logistic;

// ============================================================================
// 2. ABSTRACT CLASS (Core Identity, State, and Invariants)
// ============================================================================

abstract class Vehicle {
    // Shared State: Fields CANNOT exist in interfaces
    private final String vehicleId;
    protected double fuelOrBatteryLevel; // Enforces state across subclasses

    // Constructor: Enforces valid initialization for all child vehicles
    public Vehicle(String vehicleId, double initialPower) {
        this.vehicleId = vehicleId;
        this.fuelOrBatteryLevel = Math.min(Math.max(initialPower, 0.0), 100.0);
    }

    public String getVehicleId() {
        return vehicleId;
    }

    // Concrete Shared Behavior: Identical logic for all subclasses
    public void loadCargo(double weightKg) {
        System.out.println("Vehicle [" + vehicleId + "] loaded with " + weightKg + " kg of cargo.");
    }

    // Abstract Methods: Contract forced upon concrete subclasses
    public abstract void refuelOrCharge();
    public abstract double calculateTravelTimeHours(double distanceKm);
}
