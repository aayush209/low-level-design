package parkinglot;

import parkinglot.vehicle.Vehicle;
import parkinglot.vehicle.VehicleType;

public class ParkingSpot {

    private final String spotId;
    private final VehicleType type;
    private volatile boolean isOccupied;
    private volatile Vehicle currentVehicle;

    public ParkingSpot(String spotId, VehicleType type) {
        this.spotId = spotId;
        this.type = type;
        this.isOccupied = false;
        this.currentVehicle = null;
    }

    public String getSpotId() {
        return spotId;
    }

    public VehicleType getType() {
        return type;
    }

    public synchronized boolean assignVehicle(Vehicle vehicle) {
        if (!isOccupied && canFitVehicle(vehicle.getType())) {
            isOccupied = true;
            currentVehicle = vehicle;
            return true;
        }
        return false;
    }

    public synchronized boolean removeVehicle() {
        if (isOccupied) {
            isOccupied = false;
            currentVehicle = null;
            return true;
        }
        return false;
    }

    public boolean canFitVehicle(VehicleType vehicleType) {
        // Define logic if certain vehicleType types can fit into certain spots
        // For simplicity, assume:
        // Motorcycle spots can fit motorcycles
        // Car spots can fit cars and motorcycles
        // Truck spots can fit trucks, cars, and motorcycles
        return switch (this.type) {
            case MOTORCYCLE -> vehicleType == VehicleType.MOTORCYCLE;
            case CAR -> vehicleType == VehicleType.CAR || vehicleType == VehicleType.MOTORCYCLE;
            case TRUCK -> true; // All vehicleType types can fit
        };
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
}