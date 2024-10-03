package parkinglot;

import java.util.UUID;
import lombok.Getter;
import parkinglot.vehicle.Vehicle;

@Getter
public class ParkingTicket {

    private final UUID ticketId;
    private final String spotId;
    private final Vehicle vehicle;
    private final long entryTime;

    public ParkingTicket(String spotId, Vehicle vehicle) {
        this.ticketId = UUID.randomUUID();
        this.spotId = spotId;
        this.vehicle = vehicle;
        this.entryTime = System.currentTimeMillis();
    }
}
