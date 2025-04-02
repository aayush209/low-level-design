package uber.entities;

import lombok.Getter;
import uber.enums.Rating;

@Getter
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

    public void setDriverRating(Rating driverRating) {
        this.driverRating = driverRating;
    }
}
