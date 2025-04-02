package bookmyshow;

import java.util.List;

public class Booking {

    private final int id;
    private final Customer customer;
    private final Show show;
    private List<Seat> bookedSeats;
    private double totalAmount;
    private boolean isPaymentComplete;
    private static final double PRICE_PER_SEAT = 10.0;  // Fixed price for demo

    public Booking(int id, Customer customer, Show show) {
        this.id = id;
        this.customer = customer;
        this.show = show;
        this.isPaymentComplete = false;
        System.out.println("Generated booking id: " + id);
    }

    public boolean bookTickets(List<Seat> seats) {
        if (seats != null && !seats.isEmpty()) {
            this.bookedSeats = seats;
            calculateTotalAmount();
            return true;
        }
        return false;
    }

    private void calculateTotalAmount() {
        if (bookedSeats != null) {
            this.totalAmount = bookedSeats.size() * PRICE_PER_SEAT;
        } else {
            this.totalAmount = 0.0;
        }
    }

    public double getTotalAmount() {
        if (totalAmount == 0.0 && bookedSeats != null) {
            calculateTotalAmount();
        }
        return totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setPaymentComplete(boolean paymentComplete) {
        this.isPaymentComplete = paymentComplete;
    }

    public boolean isPaymentComplete() {
        return isPaymentComplete;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public String getPriceBreakdown() {
        return String.format(
            "Base Price: $%.2f\n" +
            "Number of Seats: %d\n" +
            "Total Amount: $%.2f",
            show.getBasePrice(),
            bookedSeats.size(),
            totalAmount
        );
    }

}
