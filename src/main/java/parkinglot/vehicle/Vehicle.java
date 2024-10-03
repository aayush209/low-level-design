package parkinglot.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Vehicle {

    protected String licensePlate;
    protected VehicleType vehicleType;
}
