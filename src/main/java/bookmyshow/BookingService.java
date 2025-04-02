package bookmyshow;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingService {
    private final ConcurrentHashMap<Integer, Booking> bookings;
    private final AtomicInteger bookingIdCounter;

    public BookingService() {
        this.bookings = new ConcurrentHashMap<>();
        this.bookingIdCounter = new AtomicInteger(1);
    }

    public Booking createBooking(Customer customer, Show show, List<Seat> seats) {
        System.out.println("Creating booking for " + customer.getName());
        
        // Try to book the seats
        if (show.bookSeats(seats)) {
            // Create and store the booking
            int bookingId = bookingIdCounter.getAndIncrement();
            Booking booking = new Booking(bookingId, customer, show);
            
            // Initialize the booking with seats
            if (booking.bookTickets(seats)) {
                bookings.put(bookingId, booking);
                return booking;
            } else {
                // If booking fails, unbook the seats
                seats.forEach(Seat::unbook);
            }
        }
        
        return null;
    }

    public boolean processPayment(Booking booking, PaymentStrategy paymentStrategy) {
        if (booking == null || booking.getBookedSeats() == null) {
            return false;
        }
        
        boolean paymentSuccess = paymentStrategy.processPayment(booking.getTotalAmount());
        
        if (paymentSuccess) {
            booking.setPaymentComplete(true);
            System.out.println("Payment processed successfully for booking ID: " + booking.getId());
        } else {
            System.out.println("Payment failed for booking ID: " + booking.getId());
            cancelBooking(booking);
        }
        
        return paymentSuccess;
    }

    private void cancelBooking(Booking booking) {
        if (booking != null && booking.getBookedSeats() != null) {
            booking.getBookedSeats().forEach(Seat::unbook);
            bookings.remove(booking.getId());
        }
    }
}
