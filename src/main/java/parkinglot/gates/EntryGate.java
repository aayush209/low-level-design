package parkinglot.gates;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import parkinglot.Level;
import parkinglot.ParkingSpot;
import parkinglot.ParkingTicket;
import parkinglot.vehicle.Vehicle;

@Slf4j
public class EntryGate {

    private final int entryGateNumber;

    public EntryGate(int entryGateNumber){
        this.entryGateNumber = entryGateNumber;
    }

    public ParkingTicket issueTicket(List<Level> levels, Vehicle vehicle){
        // search level by level for a parking spot
        for (Level level : levels) {
            ParkingSpot spot = level.findParkingSpot(vehicle);
            if (spot != null) { // if we find a spot on the current floor, generate a parking ticket
                ParkingTicket ticket = generateParkingTicket(vehicle, spot);
                log.info(
                        "Ticket issued by entry Gate : {} Parked Vehicle: {} at Spot: {} on Level: {}",
                        entryGateNumber,
                        vehicle.getLicensePlate(),
                        spot.getSpotId(),
                        level.getLevelNumber());
                return ticket;
            }
        }
        return null;
    }

    private static ParkingTicket generateParkingTicket(Vehicle vehicle, ParkingSpot spot) {
        return new ParkingTicket(spot.getSpotId(), vehicle);
    }
}
