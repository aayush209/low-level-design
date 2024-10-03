package parkinglot;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import parkinglot.vehicle.Vehicle;
import parkinglot.vehicle.VehicleType;

@Slf4j
public class ParkingLot {

    private final List<Level> levels;
    private final Map<UUID, ParkingTicket> activeTickets = new ConcurrentHashMap<>();

    public ParkingLot(List<Level> levels) {
        this.levels = levels;
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) throws Exception {
        for (Level level : levels) {
            Optional<ParkingSpot> spot = level.parkVehicle(vehicle);
            if (spot.isPresent()) {
                ParkingTicket ticket = new ParkingTicket(spot.get().getSpotId(), vehicle);
                activeTickets.put(ticket.getTicketId(), ticket);
                log.info(
                        "Parked Vehicle: {} at Spot: {} on Level: {}",
                        vehicle.getLicensePlate(),
                        spot.get().getSpotId(),
                        level.getLevelNumber());
                return ticket;
            }
        }
        throw new Exception("No available parking spots for vehicle type: " + vehicle.getType());
    }

    public void unparkVehicle(UUID ticketId) throws Exception {
        ParkingTicket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            throw new Exception("Invalid Ticket ID");
        }
        String spotId = ticket.getSpotId();
        boolean unparked = false;
        for (Level level : levels) {
            if (level.unparkVehicle(spotId)) {
                unparked = true;
                log.info(
                        "Unparked Vehicle: {} from Spot: {} on Level: {}",
                        ticket.getVehicle().getLicensePlate(),
                        spotId,
                        level.getLevelNumber());
                break;
            }
        }
        if (!unparked) {
            throw new Exception("Failed to unpark vehicle with Spot ID: " + spotId);
        }
    }

    public Map<VehicleType, Long> getAvailableSpots() {
        Map<VehicleType, Long> availability = new EnumMap<>(VehicleType.class);
        for (VehicleType type : VehicleType.values()) {
            availability.put(type, 0L);
        }

        for (Level level : levels) {
            for (VehicleType type : VehicleType.values()) {
                availability.put(type, availability.get(type) + level.getAvailableSpots(type));
            }
        }
        return availability;
    }
}
