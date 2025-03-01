package uber.strategy.pricing;

import uber.entities.TripMetaData;
import uber.utils.Util;

public class RatingBasedPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(TripMetaData metaData) {
        double price = Util.isHighRating(metaData.getRiderRating()) ? 55.0 : 65.0;
        System.out.println("Based on " + Util.ratingToString(metaData.getRiderRating()) +
                " rider rating, price of the trip is " + price);
        return price;
    }
}
