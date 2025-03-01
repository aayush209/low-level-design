package ridesharing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ridesharing.enums.DriverStatus;

@AllArgsConstructor
@Getter
@Setter
public class Driver {
    private int id;
    private String name;
    private String contact;
    private String licensePlate;
    private Location currenLocation;
    private DriverStatus status;
}
