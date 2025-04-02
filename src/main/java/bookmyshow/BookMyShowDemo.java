package bookmyshow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookMyShowDemo {

    public static void main(String[] args) {
        // A theater has one or more than cinema halls
        // A
        Theater theater = new Theater("PVR Multiplex Amanora Mall", "Pune");
        CinemaHall cinemaHall1 = new CinemaHall("Hall 1", 100);
        CinemaHall cinemaHall2 = new CinemaHall("Hall 2", 150);
        CinemaHall cinemaHall3 = new CinemaHall("Hall 3", 200);
        CinemaHall cinemaHall4 = new CinemaHall("Hall 3", 150);
        theater.addCinemaHall(cinemaHall1);
        theater.addCinemaHall(cinemaHall2);
        theater.addCinemaHall(cinemaHall3);
        theater.addCinemaHall(cinemaHall4);

        // A Show has a Movie, showTime and a CinemaHall
        Movie movie1 = new Movie("Inception", "Sci-Fi", "English", new Date());
        Show show1 = new Show(movie1, new Date(), cinemaHall1);
        Movie movie2 = new Movie("Snow White", "Fiction", "English", new Date());
        Show show2 = new Show(movie2, new Date(), cinemaHall2);
        Movie movie3 = new Movie("Alladin", "Fiction", "English", new Date());
        Show show3 = new Show(movie3, new Date(), cinemaHall3);
        Movie movie4 = new Movie("Up", "Animated", "English", new Date());
        Show show4 = new Show(movie4, new Date(), cinemaHall4);
        Movie movie5 = new Movie("Alladin", "Fiction", "Hindi", new Date());

        // A CinemaHall will have a name, List<Seat> and List<Show> shows
        cinemaHall1.addShow(show1);
        cinemaHall2.addShow(show2);
        cinemaHall3.addShow(show3);
        cinemaHall4.addShow(show4);

        SearchService searchService = new SearchService(List.of(movie1, movie2, movie3, movie4, movie5));
        MovieSearchController movieSearchController = new MovieSearchController(searchService);

        String movieNameToBeSearched = "Inception";
        String movieLanguageToBeSearched = "English";
        String movieGenreToBeSearched = "Fiction";

        System.out.println("Search movie by title " + movieNameToBeSearched + " : \n" + movieSearchController.searchByTitle(movieNameToBeSearched).toString());
        System.out.println("Search movie by Genre " + movieGenreToBeSearched + " : \n" + movieSearchController.searchByGenre(movieGenreToBeSearched).toString());
//        System.out.println(movieSearchController.searchByCity("Fiction").toString());
        System.out.println("Search movie by Language " + movieLanguageToBeSearched + " : \n" + movieSearchController.searchByLanguage(movieLanguageToBeSearched).toString());
//        System.out.println(movieSearchController.searchByReleaseDate("Fiction").toString());

        // Initialize customer and booking
        Customer customer1 = new Customer("Jake Ryan", "jake.ryan@gmail.com");
//        Customer customer2 = new Customer("Cody Rhodes", "cody.rhodes@gmail.com");
        BookingService bookingService = new BookingService();
        BookingController bookingController = new BookingController(bookingService);

        List<Seat> selectedSeats = new ArrayList<>();
        selectedSeats.add(cinemaHall1.getSeats().get(0));  // Selecting first seat
        selectedSeats.add(cinemaHall1.getSeats().get(1));  // Selecting second seat
        selectedSeats.add(cinemaHall1.getSeats().get(2));  // Selecting second seat

        // Book tickets
        boolean bookingSuccess = bookingController.bookTicket(customer1, show1, selectedSeats);

        if (bookingSuccess) {
            System.out.println("Booking successful!");
        } else {
            System.out.println("Booking failed!");
        }
    }
}