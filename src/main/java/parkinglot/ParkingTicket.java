package parkinglot;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import parkinglot.vehicle.Vehicle;

@Getter
public class ParkingTicket {

    private static final AtomicInteger counter = new AtomicInteger(1);
    private final int ticketId;
    private final String spotId;
    private final long entryTime;
    private boolean isPaid;
    private final Vehicle vehicle;

    public ParkingTicket(String spotId, Vehicle vehicle) {
        this.ticketId = counter.getAndIncrement();
        this.spotId = spotId;
        this.entryTime = System.currentTimeMillis();
        this.isPaid = false;
        this.vehicle = vehicle;
    }

    public void markTicketAsPaid(){
        this.isPaid = true;
    }
}
