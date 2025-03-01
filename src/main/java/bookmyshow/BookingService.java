package bookmyshow;

import java.util.List;
import java.util.Random;

public class BookingService {

    public boolean bookTickets(Customer customer, Show show, List<Seat> seats) {
        System.out.println("Booking tickets for " + customer.getName());
        Booking booking = new Booking(new Random().nextInt(1000));
        return booking.bookTickets(seats, show);
    }
}
