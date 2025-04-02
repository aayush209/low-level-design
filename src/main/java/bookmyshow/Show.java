package bookmyshow;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Calendar;

public class Show {

    private final Movie movie;
    private final Date showTime;
    private final CinemaHall cinemaHall;
    private final Lock bookingLock;
    private final double basePrice;
    private static final double WEEKEND_PRICE_MULTIPLIER = 1.5;
    private static final double HOLIDAY_PRICE_MULTIPLIER = 1.75;

    public Show(Movie movie, Date showTime, CinemaHall cinemaHall, double basePrice) {
        this.movie = movie;
        this.showTime = showTime;
        this.cinemaHall = cinemaHall;
        this.bookingLock = new ReentrantLock();
        this.basePrice = basePrice;
    }

    public Movie getMovie() {
        return movie;
    }

    public Date getShowTime() {
        return showTime;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public boolean isAvailable(List<Seat> seats) {
        return seats.stream().noneMatch(Seat::isBooked);
    }

    public boolean bookSeats(List<Seat> seats) {
        bookingLock.lock();
        try {
            if (isAvailable(seats)) {
                seats.forEach(seat -> seat.book());
                return true;
            }
            return false;
        } finally {
            bookingLock.unlock();
        }
    }

    public List<Seat> getAvailableSeats() {
        return cinemaHall.getSeats().stream()
                .filter(seat -> !seat.isBooked())
                .toList();
    }

    public double calculateTicketPrice() {
        double finalPrice = basePrice;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(showTime);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        
        // Weekend pricing
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            finalPrice *= WEEKEND_PRICE_MULTIPLIER;
        }
        
        // Evening show pricing (after 6 PM)
        if (hour >= 18) {
            finalPrice *= 1.2;  // 20% premium for evening shows
        }
        
        // Early morning show discount (before 12 PM)
        if (hour < 12) {
            finalPrice *= 0.9;  // 10% discount for morning shows
        }
        
        return finalPrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}

