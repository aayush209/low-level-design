package bookmyshow;

import java.util.ArrayList;
import java.util.List;

public class CinemaHall {

    private final String name;
    private final List<Seat> seats;
    private final List<Show> shows;

    public CinemaHall(String name, int totalSeats) {
        this.name = name;
        this.seats = new ArrayList<>();
        for (int seatNumber = 1; seatNumber <= totalSeats; seatNumber++) {
            this.seats.add(new Seat(seatNumber));
        }
        this.shows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void addShow(Show show) {
        this.shows.add(show);
    }
}
