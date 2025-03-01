package bookmyshow;

import java.util.List;

public class Booking {

    private final int bookingId;
    private Customer customer;
    private Show show;
    private List<Seat> bookedSeats;
    private double totalAmount;

    public Booking(int bookingId){
        this.bookingId = bookingId;
        System.out.println("Generated booking id : " + bookingId);
    }

    public boolean bookTickets(List<Seat> seats, Show show) {
        if (show.isAvailable(seats)) {
            this.bookedSeats = seats;
            show.bookSeats(seats);
            calculateTotalAmount();
            System.out.println(seats.size() + " Tickets booked for booking id : " + bookingId);
            return true;
        }
        System.out.println("Tickets could not be booked for booking id : " + bookingId);
        return false;
    }

    private void calculateTotalAmount() {
        this.totalAmount = bookedSeats.size() * 10.0;  // Sample pricing logic
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
