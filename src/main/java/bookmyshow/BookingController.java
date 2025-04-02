package bookmyshow;

import java.util.List;

public class BookingController {

    private final BookingService bookingService;
    private static final int MAX_SEATS_PER_BOOKING = 10;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Booking bookTicket(Customer customer, Show show, List<Seat> seats) {
        // Create booking
        Booking booking = bookingService.createBooking(customer, show, seats);
        
        if (booking != null) {
            // Process payment using default payment strategy
            boolean paymentSuccess = bookingService.processPayment(booking, new CreditCardPayment());
            if (paymentSuccess) {
                return booking;
            }
        }
        return null;
    }

    private boolean validateBookingRequest(Customer customer, Show show, List<Seat> seats) {
        if (customer == null || show == null || seats == null || seats.isEmpty()) {
            System.out.println("Invalid booking parameters");
            return false;
        }

        if (seats.size() > MAX_SEATS_PER_BOOKING) {
            System.out.println("Cannot book more than " + MAX_SEATS_PER_BOOKING + " seats in one booking");
            return false;
        }

        return true;
    }

    // Overloaded method that defaults to credit card payment
    public Booking bookTicket(Customer customer, Show show, List<Seat> seats, PaymentStrategy paymentStrategy) {
        return bookTicket(customer, show, seats);
    }
}
