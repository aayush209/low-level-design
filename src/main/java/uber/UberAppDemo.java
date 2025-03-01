package uber;

import java.util.Map;
import uber.entities.Driver;
import uber.entities.Location;
import uber.entities.Rider;
import uber.entities.Trip;
import uber.enums.Rating;
import uber.strategy.managers.DriverManager;
import uber.strategy.managers.RiderManager;
import uber.strategy.managers.TripManager;

public class UberAppDemo {

    public static void main(String[] args) {
        RiderManager riderManager = RiderManager.getInstance();
        DriverManager driverManager = DriverManager.getInstance();

        Rider keerti = new Rider("Keerti", Rating.FIVE_STARS);
        Rider gaurav = new Rider("Gaurav", Rating.FIVE_STARS);
        riderManager.addRider("keerti", keerti);
        riderManager.addRider("gaurav", gaurav);

        Driver yogita = new Driver("Yogita", Rating.THREE_STARS);
        Driver riddhi = new Driver("Riddhi", Rating.FOUR_STARS);
        driverManager.addDriver("yogita", yogita);
        driverManager.addDriver("riddhi", riddhi);

        TripManager tripManager = TripManager.getInstance();

        System.out.println("Creating Trip for Keerti from location (10,10) to (30,30)");
        tripManager.createTrip(keerti, new Location(10, 10), new Location(30, 30));

        System.out.println("Creating Trip for Gaurav from location (200,200) to (500,500)");
        tripManager.createTrip(gaurav, new Location(200, 200), new Location(500, 500));

        Map<Integer, Trip> trips = tripManager.getTrips();
        for (Trip trip : trips.values()) {
            trip.displayTripDetails();
        }
    }
}
