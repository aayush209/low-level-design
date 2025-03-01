package ridesharing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ridesharing.enums.RideStatus;

@AllArgsConstructor
@Getter
@Setter
public class Ride {
    @Setter(AccessLevel.NONE)
    private int id;

    @Setter(AccessLevel.NONE)
    private Passenger passenger;

    @Setter(AccessLevel.NONE)
    private Location pickUpLocation;

    @Setter(AccessLevel.NONE)
    private Location dropLocation; // assuming that we can't change the drop location to keep the scope simple

    private Driver driver; // new driver can be assigned to a ride, so we need a setter
    private RideStatus status; // rideStatus must be updated from time to time, so we need a setter
    private double fare; // fare needs to be calculated, so we need a setter
}
