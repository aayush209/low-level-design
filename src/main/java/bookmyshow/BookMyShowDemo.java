package bookmyshow;

import bookmyshow.SearchService.ShowInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.Collections;

public class BookMyShowDemo {
    private List<Movie> movies;
    private List<Theater> theaters;
    private SearchService searchService;
    private BookingService bookingService;

    public SearchService initializeSystem() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Create Movies
        this.movies = createMovies(dateFormat);
        
        // Create Theaters with Cinema Halls and Shows
        this.theaters = createTheaters(movies, dateFormat);
        
        // Initialize SearchService
        this.searchService = new SearchService(movies, theaters);
        
        // Initialize BookingService
        this.bookingService = new BookingService();
        
        return searchService;
    }

    public static void main(String[] args) {
        try {
            BookMyShowDemo demo = new BookMyShowDemo();
            demo.initializeSystem();
            
            System.out.println("\n=== BookMyShow System Demo ===");
            
            // Demonstrate search functionality
            demo.demonstrateSearch();
            
            // Demonstrate booking functionality
            demo.demonstrateBooking();
            
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    private List<Movie> createMovies(SimpleDateFormat dateFormat) throws ParseException {
        List<Movie> movies = new ArrayList<>();
        
        // Action Movies
        movies.add(new Movie("Inception", "Sci-Fi Thriller", "English", dateFormat.parse("2023-12-15")));
        movies.add(new Movie("The Dark Knight", "Action Crime Drama", "English", dateFormat.parse("2023-12-20")));
        movies.add(new Movie("Avengers: Endgame", "Action Adventure Sci-Fi", "English", dateFormat.parse("2023-12-25")));
        
        // Bollywood Movies
        movies.add(new Movie("Pathaan", "Action Thriller", "Hindi", dateFormat.parse("2023-12-18")));
        movies.add(new Movie("Jawan", "Action Drama", "Hindi", dateFormat.parse("2023-12-22")));
        
        // Regional Movies
        movies.add(new Movie("RRR", "Action Drama Historical", "Telugu", dateFormat.parse("2023-12-10")));
        movies.add(new Movie("KGF: Chapter 2", "Action Crime Drama", "Kannada", dateFormat.parse("2023-12-28")));
        movies.add(new Movie("Master", "Action Thriller", "Tamil", dateFormat.parse("2023-12-30")));
        
        // Comedy and Drama
        movies.add(new Movie("3 Idiots", "Comedy Drama", "Hindi", dateFormat.parse("2023-12-16")));
        movies.add(new Movie("The Grand Budapest Hotel", "Comedy Drama", "English", dateFormat.parse("2023-12-19")));
        
        return movies;
    }

    private List<Theater> createTheaters(List<Movie> movies, SimpleDateFormat dateFormat) throws ParseException {
        List<Theater> theaters = new ArrayList<>();
        SimpleDateFormat showTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Mumbai Theaters
        Theater inoxMumbai = new Theater("INOX Megaplex - Inorbit Mall, Malad West", "Mumbai");
        CinemaHall hall1Inox = new CinemaHall("Hall 1", 100);
        CinemaHall hall2Inox = new CinemaHall("Hall 2", 80);
        inoxMumbai.setCinemaHalls(Arrays.asList(hall1Inox, hall2Inox));
        
        Theater pvrMumbai = new Theater("PVR Icon - Phoenix Palladium, Lower Parel", "Mumbai");
        CinemaHall hall1Pvr = new CinemaHall("Hall 1", 120);
        CinemaHall hall2Pvr = new CinemaHall("Hall 2", 90);
        pvrMumbai.setCinemaHalls(Arrays.asList(hall1Pvr, hall2Pvr));
        
        // Delhi Theaters
        Theater pvrDelhi = new Theater("PVR Select City Walk - Saket", "Delhi");
        CinemaHall hall1PvrDelhi = new CinemaHall("Hall 1", 100);
        CinemaHall hall2PvrDelhi = new CinemaHall("Hall 2", 85);
        pvrDelhi.setCinemaHalls(Arrays.asList(hall1PvrDelhi, hall2PvrDelhi));
        
        // Bangalore Theaters
        Theater inoxBangalore = new Theater("INOX Garuda - Magrath Road", "Bangalore");
        CinemaHall hall1InoxBlr = new CinemaHall("Hall 1", 110);
        CinemaHall hall2InoxBlr = new CinemaHall("Hall 2", 95);
        inoxBangalore.setCinemaHalls(Arrays.asList(hall1InoxBlr, hall2InoxBlr));
        
        // Add specific shows to each hall with different movies
        // INOX Mumbai
        addSpecificShowsToHall(hall1Inox, Arrays.asList(
            movies.get(0),  // Inception
            movies.get(1)   // The Dark Knight
        ), showTimeFormat);
        
        addSpecificShowsToHall(hall2Inox, Arrays.asList(
            movies.get(3),  // Pathaan
            movies.get(8)   // 3 Idiots
        ), showTimeFormat);
        
        // PVR Mumbai
        addSpecificShowsToHall(hall1Pvr, Arrays.asList(
            movies.get(0),  // Inception
            movies.get(2),  // Avengers: Endgame
            movies.get(4)   // Jawan
        ), showTimeFormat);
        
        addSpecificShowsToHall(hall2Pvr, Arrays.asList(
            movies.get(2),  // Avengers: Endgame
            movies.get(8)   // 3 Idiots
        ), showTimeFormat);
        
        // PVR Delhi
        addSpecificShowsToHall(hall1PvrDelhi, Arrays.asList(
            movies.get(2),  // Avengers: Endgame
            movies.get(3),  // Pathaan
            movies.get(4)   // Jawan
        ), showTimeFormat);
        
        addSpecificShowsToHall(hall2PvrDelhi, Arrays.asList(
            movies.get(5),  // RRR
            movies.get(8)   // 3 Idiots
        ), showTimeFormat);
        
        // INOX Bangalore
        addSpecificShowsToHall(hall1InoxBlr, Arrays.asList(
            movies.get(2),  // Avengers: Endgame
            movies.get(6),  // KGF: Chapter 2
            movies.get(7)   // Master
        ), showTimeFormat);
        
        addSpecificShowsToHall(hall2InoxBlr, Arrays.asList(
            movies.get(5),  // RRR
            movies.get(6)   // KGF: Chapter 2
        ), showTimeFormat);
        
        theaters.addAll(Arrays.asList(inoxMumbai, pvrMumbai, pvrDelhi, inoxBangalore));
        return theaters;
    }

    private void addSpecificShowsToHall(CinemaHall hall, List<Movie> moviesForHall, SimpleDateFormat showTimeFormat) throws ParseException {
        List<Show> shows = new ArrayList<>();
        String[] showTimes = {"10:00", "13:00", "16:00", "19:00", "22:00"};
        String baseDate = "2023-12-25 ";
        
        // Define base prices for different show times
        double[] basePrices = {150.0, 200.0, 200.0, 250.0, 250.0};  // Different prices for different show times
        
        // Add shows for specific movies
        for (int i = 0; i < moviesForHall.size(); i++) {
            Movie movie = moviesForHall.get(i);
            int timeSlot = i % showTimes.length;
            
            // Calculate base price based on movie type and show time
            double showBasePrice = calculateShowPrice(movie, basePrices[timeSlot]);
            
            Show show = new Show(
                movie,
                showTimeFormat.parse(baseDate + showTimes[timeSlot]),
                hall,
                showBasePrice
            );
            shows.add(show);
        }
        
        hall.setShows(shows);
    }

    private double calculateShowPrice(Movie movie, double basePrice) {
        // Premium pricing for new releases (within last 7 days)
        Date now = new Date();
        long daysDifference = (now.getTime() - movie.getReleaseDate().getTime()) / (1000 * 60 * 60 * 24);
        
        if (daysDifference <= 7) {
            basePrice *= 1.2;  // 20% premium for new releases
        }
        
        // Premium pricing for specific languages or genres
        if (movie.getLanguage().equalsIgnoreCase("English")) {
            basePrice *= 1.1;  // 10% premium for English movies
        }
        
        if (movie.getGenre().toLowerCase().contains("3d") || 
            movie.getGenre().toLowerCase().contains("imax")) {
            basePrice *= 1.5;  // 50% premium for 3D or IMAX movies
        }
        
        return basePrice;
    }

    private void demonstrateSearch() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("\n=== Search Functionality Demo ===\n");

        // 1. Search for a movie showing in multiple theaters
        System.out.println("1. Searching for 'Avengers: Endgame' (showing in multiple theaters):");
        List<Movie> avengersResults = searchService.searchByTitle("Avengers");
        printMoviesWithTheaters(avengersResults);

        // 2. Search for a movie showing in limited theaters
        System.out.println("\n2. Searching for 'Inception' (showing in limited theaters):");
        List<Movie> inceptionResults = searchService.searchByTitle("Inception");
        printMoviesWithTheaters(inceptionResults);

        // 3. Search for a regional movie
        System.out.println("\n3. Searching for 'KGF' (showing only in Bangalore):");
        List<Movie> kgfResults = searchService.searchByTitle("KGF");
        printMoviesWithTheaters(kgfResults);

        // 4. Search by language (Hindi movies)
        System.out.println("\n4. Searching for Hindi movies:");
        List<Movie> hindiResults = searchService.searchByLanguage("Hindi");
        printMoviesWithTheaters(hindiResults);

        // 5. Search by city
        System.out.println("\n5. Searching movies in Mumbai:");
        List<Movie> mumbaiResults = searchService.searchByCity("Mumbai");
        printMoviesWithTheaters(mumbaiResults);
    }

    private void printMoviesWithTheaters(List<Movie> movies) {
        if (movies.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }
        
        for (Movie movie : movies) {
            System.out.println("\nMovie: " + movie.getTitle());
            System.out.println("Genre: " + movie.getGenre());
            System.out.println("Language: " + movie.getLanguage());
            System.out.println("Release Date: " + movie.getReleaseDate());
            
            // Get and print show details for this movie
            List<ShowInfo> showDetails = searchService.getShowDetails(movie, null);
            System.out.println("Showing in theaters:");
            if (showDetails.isEmpty()) {
                System.out.println("- No shows scheduled");
            } else {
                Set<String> uniqueTheaters = new HashSet<>();
                for (ShowInfo showInfo : showDetails) {
                    String theaterInfo = String.format("- %s (%s)", 
                        showInfo.getTheater().getName(),
                        showInfo.getTheater().getCity());
                    uniqueTheaters.add(theaterInfo);
                }
                uniqueTheaters.forEach(System.out::println);
            }
            System.out.println("---");
        }
    }

    private void demonstrateBooking() {
        System.out.println("\n=== Booking Functionality Demo ===\n");

        try {
            // Get a show for booking
            Theater theater = theaters.get(0);
            CinemaHall hall = theater.getCinemaHalls().get(0);
            Show show = hall.getShows().get(0);
        BookingController bookingController = new BookingController(bookingService);

            // First demonstrate single successful booking
            System.out.println("=== Scenario 1: Single Booking ===");
            Customer customer1 = new Customer("John Doe", "john@example.com");
            List<Seat> seats1 = Arrays.asList(
                hall.getSeats().get(3),
                hall.getSeats().get(4)
            );

            System.out.println("\nAttempting booking for " + customer1.getName());
            System.out.println("Movie: " + show.getMovie().getTitle());
            System.out.println("Theater: " + theater.getName());
            System.out.println("Selected Seats: " + seats1.stream()
                .map(seat -> String.valueOf(seat.getSeatNumber()))
                .collect(Collectors.joining(", ")));

            Booking booking1 = bookingController.bookTicket(customer1, show, seats1);
            printBookingResult("First Booking", booking1);

            // Second successful booking
            Customer customer2 = new Customer("Jane Doe", "jane@example.com");
            List<Seat> seats2 = Arrays.asList(
                hall.getSeats().get(7),
                hall.getSeats().get(8)
            );

            System.out.println("\nAttempting booking for " + customer2.getName());
            System.out.println("Movie: " + show.getMovie().getTitle());
            System.out.println("Theater: " + theater.getName());
            System.out.println("Selected Seats: " + seats2.stream()
                .map(seat -> String.valueOf(seat.getSeatNumber()))
                .collect(Collectors.joining(", ")));

            Booking booking2 = bookingController.bookTicket(customer2, show, seats2);
            printBookingResult("Second Booking", booking2);

            // Then demonstrate concurrent booking scenario
            System.out.println("\n=== Scenario 2: Concurrent Booking ===");
            
            Runnable bookingTask = () -> {
                try {
                    String threadName = Thread.currentThread().getName();
                    Customer customer = new Customer(threadName, threadName + "@example.com");
                    
                    // Try to book the same seats
                    List<Seat> seats = Arrays.asList(
                        hall.getSeats().get(1),
                        hall.getSeats().get(2)
                    );

                    System.out.println("\n" + threadName + " attempting to book seats...");
                    Booking booking = bookingController.bookTicket(customer, show, seats);
                    printBookingResult(threadName, booking);
                    
                } catch (Exception e) {
                    System.out.println("Error in booking thread: " + e.getMessage());
                }
            };

            // Create and start concurrent booking threads
            Thread user1 = new Thread(bookingTask, "User1");
            Thread user2 = new Thread(bookingTask, "User2");

            user1.start();
            user2.start();

            user1.join();
            user2.join();

            // Show final seat status
            System.out.println("\nFinal Seat Status:");
            printSeatStatus(hall);

        } catch (Exception e) {
            System.out.println("Error during booking demonstration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void printBookingResult(String bookingName, Booking booking) {
        if (booking != null && booking.getBookedSeats() != null) {
            System.out.println("\n" + bookingName + ": Booking Successful!");
            System.out.println("Booking ID: " + booking.getId());
            System.out.println("Number of Seats: " + booking.getBookedSeats().size());
            System.out.println("Booked Seats: " + booking.getBookedSeats().stream()
                .map(seat -> String.valueOf(seat.getSeatNumber()))
                .collect(Collectors.joining(", ")));
            System.out.println("Total Amount Paid: $" + String.format("%.2f", booking.getTotalAmount()));
        } else {
            System.out.println("\n" + bookingName + ": Booking Failed!");
        }
    }

    private void printSeatStatus(CinemaHall hall) {
        System.out.println("\nCurrent seat status (first 10 seats):");
        hall.getSeats().stream()
            .limit(10)
            .forEach(seat -> System.out.println("Seat " + seat.getSeatNumber() + ": " + 
                (seat.isBooked() ? "Booked" : "Available")));
    }
}