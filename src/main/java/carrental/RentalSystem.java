package carrental;

import carrental.payment.CreditCardPaymentProcessor;
import carrental.payment.PaymentProcessor;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RentalSystem {
    private static RentalSystem instance;
    private Map<String, Car> cars;
    private Map<String, Reservation> reservations;
    private PaymentProcessor paymentProcessor;
    private SearchService searchService;

    private RentalSystem(){
        cars = new ConcurrentHashMap<>();
        reservations = new ConcurrentHashMap<>();
        paymentProcessor = new CreditCardPaymentProcessor();
    }
    public static RentalSystem getInstance(){
        // double locking
        if(instance == null){
            synchronized (RentalSystem.class){
                if(instance == null){
                    instance = new RentalSystem();
                }
            }
        }
        return instance;
    }

    public void addCar(Car car){
        cars.put(car.getLicensePlate(), car);
    }

    public void removeCar(String licensePlate){
        cars.remove(licensePlate);
    }

    public List<Car> searchCarsByModelAndPrice(String model, double maxPrice, LocalDate startDate, LocalDate endDate) {
        return searchService.searchCarsByModelAndPrice(model, maxPrice, startDate, endDate);
    }

    public synchronized Reservation makeReservation(Customer customer, Car car, LocalDate startDate, LocalDate endDate) {
        if (searchService.isCarAvailable(car, startDate, endDate)) {
            String reservationId = generateReservationId();
            Reservation reservation = new Reservation(reservationId, customer, car, startDate, endDate);
            reservations.put(reservationId, reservation);
            car.setAvailability(false);
            return reservation;
        }
        return null;
    }

    public synchronized void cancelReservation(String reservationId) {
        Reservation reservation = reservations.remove(reservationId);
        if (reservation != null) {
            reservation.getRentedCar().setAvailability(true);
        }
    }

    public boolean processPayment(Reservation reservation) {
        return paymentProcessor.processPayment(reservation.getTotalPrice());
    }

    private String generateReservationId() {
        return "RES" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
