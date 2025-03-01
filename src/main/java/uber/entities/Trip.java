package uber.entities;

import uber.enums.TripStatus;
import uber.strategy.drivermatching.DriverMatchingStrategy;
import uber.strategy.pricing.PricingStrategy;

public class Trip {

    private static int nextTripId = 1;

    private final Rider rider;
    private final Driver driver;
    private final Location srcLoc;
    private final Location dstLoc;
    private TripStatus status;
    private final int tripId;
    private final double price;
    private PricingStrategy pricingStrategy;
    private DriverMatchingStrategy driverMatchingStrategy;

    public Trip(Rider rider, Driver driver, Location srcLoc, Location dstLoc, double price,
            PricingStrategy pricingStrategy, DriverMatchingStrategy driverMatchingStrategy) {
        this.rider = rider;
        this.driver = driver;
        this.srcLoc = srcLoc;
        this.dstLoc = dstLoc;
        this.price = price;
        this.pricingStrategy = pricingStrategy;
        this.driverMatchingStrategy = driverMatchingStrategy;
        this.status = TripStatus.DRIVER_ON_THE_WAY;
        this.tripId = nextTripId++;
    }

    public int getTripId() {
        return tripId;
    }

    public void displayTripDetails() {
        System.out.println();
        System.out.println("Trip id - " + tripId);
        System.out.println("Rider - " + rider.getName());
        System.out.println("Driver - " + driver.getName());
        System.out.println("Price - " + price);
        System.out.println("Locations - " + srcLoc.getLatitude() + "," + srcLoc.getLongitude() + " and " + dstLoc.getLatitude() + "," + dstLoc.getLongitude());
    }
}
