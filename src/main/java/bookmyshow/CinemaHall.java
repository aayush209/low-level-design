package bookmyshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CinemaHall {

    private final String name;
    private final int capacity;
    private List<Seat> seats;
    private List<Show> shows;
    private final Map<Integer, Seat> seatMap; // Quick lookup by seat number

    public CinemaHall(String name, int totalSeats) {
        this.name = name;
        this.capacity = totalSeats;
        this.seats = new ArrayList<>(totalSeats);
        this.seatMap = new HashMap<>(totalSeats);
        this.shows = new ArrayList<>();
        
        initializeSeats(totalSeats);
    }

    private void initializeSeats(int totalSeats) {
        for (int seatNumber = 1; seatNumber <= totalSeats; seatNumber++) {
            Seat seat = new Seat(seatNumber);
            seats.add(seat);
            seatMap.put(seatNumber, seat);
        }
    }

    public Seat getSeatByNumber(int seatNumber) {
        return seatMap.get(seatNumber);
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
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

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
