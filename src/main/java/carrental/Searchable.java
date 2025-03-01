package carrental;

import java.time.LocalDate;
import java.util.List;

public interface Searchable {

    List<Car> searchCarsByModelAndPrice(String model, double maxPrice, LocalDate startDate, LocalDate endDate);

    List<Car> searchCarsByPrice(double minPrice, double maxPrice, LocalDate startDate, LocalDate endDate);
}