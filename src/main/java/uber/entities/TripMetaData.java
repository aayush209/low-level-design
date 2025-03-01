package uber.entities;

import uber.enums.Rating;

public class TripMetaData {

    private Location srcLoc;
    private Location dstLoc;
    private final Rating riderRating;
    private Rating driverRating;

    public TripMetaData(Location srcLoc, Location dstLoc, Rating riderRating) {
        this.srcLoc = srcLoc;
        this.dstLoc = dstLoc;
        this.riderRating = riderRating;
        this.driverRating = Rating.UNASSIGNED;
    }

    public Rating getRiderRating() {
        return riderRating;
    }

    public Rating getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(Rating driverRating) {
        this.driverRating = driverRating;
    }
}
