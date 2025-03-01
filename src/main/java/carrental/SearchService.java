package carrental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchService implements Searchable{
    private final Map<String, Car> cars;
    private final Map<String, Reservation> reservations;

    public SearchService(Map<String, Car> cars, Map<String, Reservation> reservations) {
        this.cars = cars;
        this.reservations = reservations;
    }

    @Override
    public List<Car> searchCarsByModelAndPrice(String model, double maxPrice, LocalDate startDate, LocalDate endDate) {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars.values()) {
            if (car.getModel().equalsIgnoreCase(model) && car.getPricePerDay() <= maxPrice && car.isAvailable()) {
                if (isCarAvailable(car, startDate, endDate)) {
                    availableCars.add(car);
                }
            }
        }
        return availableCars;
    }

    public boolean isCarAvailable(Car car, LocalDate startDate, LocalDate endDate) {
        for (Reservation reservation : reservations.values()) {
            if (reservation.getRentedCar().equals(car)) {
                if (startDate.isBefore(reservation.getEndDate()) && endDate.isAfter(reservation.getStartDate())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<Car> searchCarsByPrice(double minPrice, double maxPrice, LocalDate startDate, LocalDate endDate) {
        return null;
    }
}