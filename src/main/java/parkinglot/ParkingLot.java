package parkinglot;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import parkinglot.gates.EntryGate;
import parkinglot.gates.ExitGate;
import parkinglot.vehicle.Vehicle;
import parkinglot.vehicle.VehicleType;

@Slf4j
public class ParkingLot {

    private final List<Level> levels;
    private final Map<Integer, ParkingTicket> activeTickets = new ConcurrentHashMap<>();
    private final Map<Integer, EntryGate> entryGates;
    private final Map<Integer, ExitGate> exitGates;

    public ParkingLot(List<Level> levels, int numEntryGates, int numExitGates) {
        this.levels = levels;
        this.entryGates = new HashMap<>();
        this.exitGates = new HashMap<>();
        initializeEntryGates(numEntryGates, entryGates);
        initializeExitGates(numExitGates, exitGates);
    }

    private void initializeEntryGates(int numEntryGates, Map<Integer, EntryGate> entryGates) {
        for (int index = 1; index <= numEntryGates; index++){
            entryGates.put(index, new EntryGate(index));
        }
    }

    private void initializeExitGates(int numExitGates, Map<Integer, ExitGate> exitGates) {
        for (int index = 1; index <= numExitGates; index++){
            exitGates.put(index, new ExitGate(index));
        }
    }

    public ParkingTicket parkVehicle(Vehicle vehicle, int entryGateNumber) {
        EntryGate entryGate = entryGates.get(entryGateNumber);
            ParkingTicket parkingTicket = entryGate.issueTicket(levels, vehicle);
            if(parkingTicket != null){
                // add ticket to active tickets
                activeTickets.put(parkingTicket.getTicketId(), parkingTicket);
                return parkingTicket;
            }

            return null;
    }

    public void unparkVehicle(int ticketNumber, int exitGateNumber) throws Exception {
        // remove ticket from active tickets
        ParkingTicket ticket = activeTickets.remove(ticketNumber);
        if (ticket == null) {
            throw new Exception("Invalid Ticket ID");
        }
        exitGates.get(exitGateNumber).processPayment(ticket);
        log.info("Payment processed for {} with license plate : {} against ticket number :{}",
                ticket.getVehicle().getVehicleType(),
                ticket.getVehicle().getLicensePlate(),
                ticketNumber);

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
