package parkinglot;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import parkinglot.vehicle.Vehicle;
import parkinglot.vehicle.VehicleType;

public class Level {

    private final int levelNumber;
    private final List<ParkingSpot> spots;
    private final ReentrantLock lock = new ReentrantLock();

    public Level(int levelNumber, List<ParkingSpot> spots) {
        this.levelNumber = levelNumber;
        this.spots = spots;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }

    public ParkingSpot findParkingSpot(Vehicle vehicle) {
        lock.lock();
        try {
            // search for spot which is free and can fit the current vehic;e
            for (ParkingSpot spot : spots) {
                if (!spot.isOccupied() && spot.canFitVehicle(vehicle.getVehicleType())) {
                    if (spot.assignVehicle(vehicle)) {
                        return spot;
                    }
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public boolean unparkVehicle(String spotId) {
        lock.lock();
        try {
            for (ParkingSpot spot : spots) {
                if (spot.getSpotId().equals(spotId) && spot.isOccupied()) {
                    return spot.removeVehicle();
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public int getAvailableSpots(VehicleType type) {
        return (int) spots.stream()
                .filter(s -> !s.isOccupied() && s.canFitVehicle(type))
                .count();
    }
}
