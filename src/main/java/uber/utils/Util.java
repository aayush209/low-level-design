package uber.utils;

import uber.enums.Rating;

public class Util {

    public static String ratingToString(Rating rating) {
        return switch (rating) {
            case ONE_STAR -> "one star";
            case TWO_STARS -> "two stars";
            case THREE_STARS -> "three stars";
            case FOUR_STARS -> "four stars";
            case FIVE_STARS -> "five stars";
            default -> "invalid rating";
        };
    }

    public static boolean isHighRating(Rating rating) {
        return rating == Rating.FOUR_STARS || rating == Rating.FIVE_STARS;
    }
}
