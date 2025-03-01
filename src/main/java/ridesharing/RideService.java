package ridesharing;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.slf4j.Slf4j;
import ridesharing.enums.DriverStatus;
import ridesharing.enums.RideStatus;

@Slf4j
public final class RideService {

    private static volatile RideService instance;

    private final Map<Integer, Passenger> passengers; // id, passenger
    private final Map<Integer, Driver> drivers; // id, driver
    private final Map<Integer, Ride> rides;  // id, ride
    private final Queue<Ride> requestedRides;


    private RideService() {
        this.passengers = new ConcurrentHashMap<>();
        this.drivers = new ConcurrentHashMap<>();
        this.rides = new ConcurrentHashMap<>();
        this.requestedRides = new ConcurrentLinkedQueue<>();
    }

    public static RideService getInstance() {
        if (instance == null) {
            synchronized (RideService.class) {
                if (instance == null) {
                    instance = new RideService();
                }
            }
        }
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning is not allowed for this class");
    }

    public void addPassenger(Passenger passenger) {
        passengers.put(passenger.getId(), passenger);
    }

    public void addDriver(Driver driver) {
        drivers.put(driver.getId(), driver);
    }

    public void requestRide(Passenger passenger, Location pickUpLocation, Location dropLocation) {
        //create a new ride and add to the requestedRides Queue
        Ride newRide = new Ride(generateRideId(), passenger, pickUpLocation, dropLocation, null, RideStatus.REQUESTED, 0.0);
        requestedRides.add(newRide);
        notifyDrivers(newRide); // notify nearby drivers about the new ride requested by the passenger
    }

    //Helper method
    private void notifyDrivers(Ride ride) {
        // notify all available drivers within 5kms of range
        for (Driver driver : drivers.values()) {
            if (driver.getStatus() == DriverStatus.AVAILABLE) {
                double distance = calculateDistance(driver.getCurrenLocation(), ride.getPickUpLocation());
                if (distance <= 5.0) {
                    log.info("Notifying driver {} : {} about the incoming ride request : {} from {}", driver.getId(), driver.getName(), ride.getId(),
                            ride.getPassenger().getName());
                    log.info("Estimated earnings : {}", calculateFare(ride));
                }
            }

        }
    }

    // ride accepted by the driver
    public void acceptRide(Driver driver, Ride ride) {
        if (ride.getStatus() == RideStatus.REQUESTED) {
            ride.setDriver(driver);
            ride.setStatus(RideStatus.ACCEPTED);
            driver.setStatus(DriverStatus.BUSY);
            notifyPassenger(ride); // inform passenger that the driver has been matched
        }
    }

    public void startRide(Ride ride) {
        if (ride.getStatus() == RideStatus.ACCEPTED) {
            ride.setStatus(RideStatus.IN_PROGRESS);
            notifyPassenger(ride); // inform passenger that the ride has been started
        }
    }

    public void completeRide(Ride ride) {
        if (ride.getStatus() == RideStatus.IN_PROGRESS) {
            ride.setStatus(RideStatus.COMPLETED);
            ride.getDriver().setStatus(DriverStatus.AVAILABLE); // driver becomes available
            double fare = calculateFare(ride);
            ride.setFare(fare);
            processPayment(ride, fare);
            notifyPassenger(ride);  // inform passenger that the ride has been completed
            notifyDriver(ride);  // inform driver that the ride has been completed
            notifyPassenger(ride);
        }
    }

    public void cancelRide(Ride ride){
        if(ride.getStatus() == RideStatus.IN_PROGRESS || ride.getStatus() == RideStatus.ACCEPTED){
            ride.setStatus(RideStatus.CANCELLED);
            if(ride.getDriver() != null){
                ride.getDriver().setStatus(DriverStatus.AVAILABLE);
                //notify if there is a driver assigned to the current ride
                notifyDriver(ride);
            }
            notifyPassenger(ride);
        }
    }

    private void processPayment(Ride ride, double fare) {
        // add some logic to process the fare
        log.info("Payment of Rs. {} processed for passenger : {}", fare, ride.getPassenger().getName());
    }

    private double calculateFare(Ride ride) {
        double baseFare = 2.0;
        double perKmFare = 1.5;
        double perMinFare = 0.25;

        double distance = calculateDistance(ride.getPickUpLocation(), ride.getDropLocation()); // in kms
        double duration = calculateDuration(distance); // in mins

        double fare = baseFare + perKmFare * distance + perMinFare * duration;
        return Math.round(fare * 100.0) / 100.0; // round to 2 decimal places
    }

    private double calculateDuration(double distance) {
        // assume drivers drive at 40 km per hour speed
        return (distance / 40) * 60; // in mins
    }

    private void notifyPassenger(Ride ride) {
        Passenger passenger = ride.getPassenger();
        String message = "";
        switch (ride.getStatus()) {
            case ACCEPTED -> {
                message = "Your ride has been ACCEPTED by driver : " + ride.getDriver().getName();
                break;
            }
            case IN_PROGRESS -> {
                message = "Your ride is in Progress now";
                break;
            }
            case COMPLETED -> {
                message = "Your ride has been COMPLETED";
                break;
            }
            case CANCELLED -> {
                message = "Your ride has been CANCELLED";
                break;
            }
        }
        // notify the passenger
        log.info("Notifying Passenger : {} - {}", ride.getPassenger().getName(), message);
    }


    private void notifyDriver(Ride ride) {
        Driver driver = ride.getDriver();
        if(driver != null){
            String message = "";
            switch (ride.getStatus()) {
                case COMPLETED -> {
                    message = "Ride COMPLETED. Fare Rs. " + ride.getFare();
                    break;
                }
                case CANCELLED -> {
                    message = "Ride CANCELLED by the passenger";
                    break;
                }
            }
            // notify the driver
            log.info("Notifying Driver : {} - {}", ride.getPassenger().getName(), message);
        }
    }

    // helper method
    private double calculateDistance(Location currenLocation, Location pickUpLocation) {
        return Math.random() * 20 + 1; // for simplicity, else we can add logic to calculate distance here
    }

    //Helper method
    private int generateRideId() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    //getters
    public Map<Integer, Ride> getRides() {
        return rides;
    }

    public Queue<Ride> getRequestedRides() {
        return requestedRides;
    }

}
