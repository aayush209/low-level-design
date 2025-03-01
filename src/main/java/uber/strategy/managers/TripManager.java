package uber.strategy.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import uber.entities.Driver;
import uber.entities.Location;
import uber.entities.Rider;
import uber.entities.Trip;
import uber.entities.TripMetaData;
import uber.strategy.drivermatching.DriverMatchingStrategy;
import uber.strategy.drivermatching.LeastTimeBasedMatchingStrategy;
import uber.strategy.pricing.DefaultPricingStrategy;
import uber.strategy.pricing.PricingStrategy;

public class TripManager {

    private static TripManager instance;
    private static final Lock lock = new ReentrantLock();
    private final Map<Integer, Trip> tripsInfo = new HashMap<>();

    private TripManager() {

    }

    public static TripManager getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new TripManager();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void createTrip(Rider rider, Location srcLoc, Location dstLoc) {
        TripMetaData metaData = new TripMetaData(srcLoc, dstLoc, rider.getRating());

        PricingStrategy pricingStrategy = new DefaultPricingStrategy();
        DriverMatchingStrategy driverMatchingStrategy = new LeastTimeBasedMatchingStrategy();

        Driver driver = driverMatchingStrategy.matchDriver(metaData);
        if (driver == null) {
            return;
        }

        double price = pricingStrategy.calculatePrice(metaData);
        Trip trip = new Trip(rider, driver, srcLoc, dstLoc, price, pricingStrategy, driverMatchingStrategy);
        tripsInfo.put(trip.getTripId(), trip);
    }

    public Map<Integer, Trip> getTrips() {
        return tripsInfo;
    }
}
