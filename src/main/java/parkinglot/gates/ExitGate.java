package parkinglot.gates;

import parkinglot.ParkingTicket;

// Class representing an Exit Gate
public class ExitGate {

    private final int exitGateNumber;

    public ExitGate(int entryGateNumber){
        this.exitGateNumber = entryGateNumber;
    }

    private static final double HOURLY_RATE = 10.0;

    public double processPayment(ParkingTicket ticket) {
        long duration = (System.currentTimeMillis() - ticket.getEntryTime()) / 3600000; // convert to hours
        double amount = Math.max(1, duration) * HOURLY_RATE; // minimum charge for 1 hour
        ticket.markTicketAsPaid();
        return amount;
    }
}
