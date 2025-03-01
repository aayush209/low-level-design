package bookmyshow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookMyShowDemo {

    public static void main(String[] args) {
        // Initialize data
        Theater theater = new Theater("PVR Multiplex Amanora Mall", "Pune");
        CinemaHall cinemaHall = new CinemaHall("Hall 1", 100);
        theater.addCinemaHall(cinemaHall);

        Movie movie = new Movie("Inception", "Sci-Fi", "English", new Date());
        Show show = new Show(movie, new Date(), cinemaHall);
        cinemaHall.addShow(show);

        // Initialize customer and booking
        Customer customer1 = new Customer("Jake Ryan", "jake.ryan@gmail.com");
//        Customer customer2 = new Customer("Cody Rhodes", "cody.rhodes@gmail.com");
        BookingService bookingService = new BookingService();
        BookingController bookingController = new BookingController(bookingService);

        List<Seat> selectedSeats = new ArrayList<>();
        selectedSeats.add(cinemaHall.getSeats().get(0));  // Selecting first seat
        selectedSeats.add(cinemaHall.getSeats().get(1));  // Selecting second seat
        selectedSeats.add(cinemaHall.getSeats().get(2));  // Selecting second seat

        // Book tickets
        boolean bookingSuccess = bookingController.bookTicket(customer1, show, selectedSeats);

        if (bookingSuccess) {
            System.out.println("Booking successful!");
        } else {
            System.out.println("Booking failed!");
        }
    }
}