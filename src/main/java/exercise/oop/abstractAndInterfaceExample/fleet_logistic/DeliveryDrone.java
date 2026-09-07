package exercise.oop.abstractAndInterfaceExample.fleet_logistic;

// DeliveryDrone: Is a Vehicle + Is GPSDependable + Is AutonomousCapable (Multiple Interfaces)
class DeliveryDrone extends Vehicle implements GPSDependable, AutonomousCapable {

    public DeliveryDrone(String vehicleId, double batteryLevel) {
        super(vehicleId, batteryLevel);
    }

    @Override
    public void refuelOrCharge() {
        this.fuelOrBatteryLevel = 100.0;
        System.out.println("Drone " + getVehicleId() + ": Swapped battery pack to 100%.");
    }

    @Override
    public double calculateTravelTimeHours(double distanceKm) {
        return distanceKm / 120.0; // Direct air-line speed: 120 km/h
    }

    @Override
    public String getCoordinates() {
        return "37.7833° N, 122.4167° W (Altitude: 120m)";
    }

    @Override
    public void navigateToDestination(String destination) {
        System.out.println("Drone " + getVehicleId() + " flying in direct air corridor to " + destination);
    }
}
