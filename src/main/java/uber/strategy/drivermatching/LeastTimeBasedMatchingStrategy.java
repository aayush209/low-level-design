package uber.strategy.drivermatching;

import uber.entities.Driver;
import uber.entities.TripMetaData;
import uber.strategy.managers.DriverManager;

public class LeastTimeBasedMatchingStrategy implements DriverMatchingStrategy {

    @Override
    public Driver matchDriver(TripMetaData metaData) {
        DriverManager driverManager = DriverManager.getInstance();

        if (driverManager.getDrivers().isEmpty()) {
            System.out.println("No drivers! What service is this huh?");
            return null;
        }

        System.out.println("Using quadtree to see nearest cabs, using driver manager to get details of drivers and send notifications");
        Driver driver = driverManager.getDrivers().values().iterator().next();
        System.out.println("Setting " + driver.getName() + " as driver");
        metaData.setDriverRating(driver.getRating());
        return driver;
    }
}
