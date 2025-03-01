package bookmyshow;

import java.util.ArrayList;
import java.util.List;

public class Theater {

    private final String name;
    private final String city;
    private final List<CinemaHall> cinemaHalls;

    public Theater(String name, String city) {
        this.name = name;
        this.city = city;
        this.cinemaHalls = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public List<CinemaHall> getCinemaHalls() {
        return cinemaHalls;
    }

    public void addCinemaHall(CinemaHall hall) {
        this.cinemaHalls.add(hall);
    }
}
