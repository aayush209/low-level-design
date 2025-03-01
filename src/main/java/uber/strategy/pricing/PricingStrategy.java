package uber.strategy.pricing;

import uber.entities.TripMetaData;

public interface PricingStrategy {

    double calculatePrice(TripMetaData metaData);
}
