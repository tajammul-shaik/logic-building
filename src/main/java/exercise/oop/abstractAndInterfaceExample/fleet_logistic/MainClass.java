package exercise.oop.abstractAndInterfaceExample.fleet_logistic;

// ============================================================================
// 4. MAIN EXECUTION DEMO
// ============================================================================

public class MainClass {
    public static void main(String[] args) {

        // Instantiate Concrete Objects
        DeliveryTruck truck = new DeliveryTruck("TRK-808", 45.0);
        DeliveryDrone drone = new DeliveryDrone("DRN-909", 85.0);
        SmartPackageTracker packageTag = new SmartPackageTracker("PKG-100234");

        System.out.println("--- 1. ABSTRACT CLASS STATE & BEHAVIOR ---");
        truck.loadCargo(1500.0);
        truck.refuelOrCharge();
        System.out.println("Truck travel time for 180 km: " + truck.calculateTravelTimeHours(180) + " hours");

        System.out.println("\n--- 2. INTERFACE POLYMORPHISM Across Unrelated Types ---");
        // Polymorphic array mixing Vehicles and Packages via their shared Interface
        GPSDependable[] trackableAssets = { truck, drone, packageTag };

        for (GPSDependable asset : trackableAssets) {
            asset.logCurrentLocation(); // Executes interface default method
        }

        System.out.println("\n--- 3. MULTIPLE INTERFACE INHERITANCE & MODERN FEATURES ---");
        // Drone uses Vehicle abstract rules + AutonomousCapable interface default/private pipeline
        drone.executeAutonomousMission("Customer Zone B");
    }
}
