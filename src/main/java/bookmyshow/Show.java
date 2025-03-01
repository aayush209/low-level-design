package bookmyshow;

import java.util.Date;
import java.util.List;

public class Show {

    private final Movie movie;
    private final Date showTime;
    private final CinemaHall cinemaHall;

    public Show(Movie movie, Date showTime, CinemaHall cinemaHall) {
        this.movie = movie;
        this.showTime = showTime;
        this.cinemaHall = cinemaHall;
    }

    public Movie getMovie() {
        return movie;
    }

    public Date getShowTime() {
        return showTime;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public boolean isAvailable(List<Seat> seats) {
        for (Seat seat : seats) {
            if (seat.isBooked()) {
                return false;
            }
        }
        return true;
    }

    public boolean bookSeats(List<Seat> seats) {
        if (isAvailable(seats)) {
            for (Seat seat : seats) {
                seat.book();
                System.out.println("Booked seat no. : " + seat.getSeatNumber());
            }
            return true;
        }
        return false;
    }

    public List<Seat> getAvailableSeats() {
        return cinemaHall.getSeats().stream()
                .filter(seat -> !seat.isBooked())
                .toList();
    }
}

