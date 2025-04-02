package bookmyshow;

import java.util.concurrent.atomic.AtomicBoolean;

public class Seat {

    private final int seatNumber;
    private final AtomicBoolean isBooked;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = new AtomicBoolean(false);
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked.get();
    }

    public boolean book() {
        return isBooked.compareAndSet(false, true);
    }

    public boolean unbook() {
        return isBooked.compareAndSet(true, false);
    }
}
