package exercise.oop.abstractAndInterfaceExample.fleet_logistic;

// ============================================================================
// 3. CONCRETE IMPLEMENTATIONS
// ============================================================================

// DeliveryTruck: Is a Vehicle + Is GPSDependable
class DeliveryTruck extends Vehicle implements GPSDependable {

    public DeliveryTruck(String vehicleId, double fuelLevel) {
        super(vehicleId, fuelLevel); // Calls abstract parent constructor
    }

    @Override
    public void refuelOrCharge() {
        this.fuelOrBatteryLevel = 100.0;
        System.out.println("Truck " + getVehicleId() + ": Filled diesel tank to 100%.");
    }

    @Override
    public double calculateTravelTimeHours(double distanceKm) {
        return distanceKm / 60.0; // Average ground speed: 60 km/h
    }

    @Override
    public String getCoordinates() {
        return "37.7749° N, 122.4194° W (Highway 101)";
    }
}
