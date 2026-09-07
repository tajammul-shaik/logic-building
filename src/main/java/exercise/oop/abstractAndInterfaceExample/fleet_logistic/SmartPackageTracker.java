package exercise.oop.abstractAndInterfaceExample.fleet_logistic;

// SmartPackageTracker: NOT a Vehicle, but IS GPSDependable
class SmartPackageTracker implements GPSDependable {
    private final String trackingNumber;

    public SmartPackageTracker(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Override
    public String getCoordinates() {
        return "37.7750° N, 122.4180° W (Inside Transit Hub)";
    }
}
