package uber.entities;

import uber.enums.Rating;

public class Rider {

    private final String name;
    private final Rating rating;

    public Rider(String name, Rating rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }
}
