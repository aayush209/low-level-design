package uber.strategy.drivermatching;

import uber.entities.Driver;
import uber.entities.TripMetaData;

public interface DriverMatchingStrategy {

    Driver matchDriver(TripMetaData metaData);
}
