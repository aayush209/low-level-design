package parkinglot;

import java.util.List;
import java.util.Optional;
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

    public Optional<ParkingSpot> parkVehicle(Vehicle vehicle) {
        lock.lock();
        try {
            for (ParkingSpot spot : spots) {
                if (!spot.isOccupied() && spot.canFitVehicle(vehicle.getType())) {
                    if (spot.assignVehicle(vehicle)) {
                        return Optional.of(spot);
                    }
                }
            }
            return Optional.empty();
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

    public long getAvailableSpots(VehicleType type) {
        return spots.stream().filter(s -> !s.isOccupied() && s.canFitVehicle(type)).count();
    }
}
