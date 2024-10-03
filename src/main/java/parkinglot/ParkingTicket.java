package parkinglot;

import java.util.UUID;
import parkinglot.vehicle.Vehicle;

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

    public UUID getTicketId() {
        return ticketId;
    }

    public String getSpotId() {
        return spotId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getEntryTime() {
        return entryTime;
    }
}