package bookmyshow;

import java.util.List;

public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public boolean bookTicket(Customer customer, Show show, List<Seat> seats) {
        return bookingService.bookTickets(customer, show, seats);
    }
}
