package uber.entities;

import uber.enums.Rating;

public class Driver {

    private final String name;
    private final Rating rating;
    private boolean available;

    public Driver(String name, Rating rating) {
        this.name = name;
        this.rating = rating;
        this.available = false;
    }

    public void updateAvailability(boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }
}
