package carrental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private String reservationId;
    private Customer customer;
    private Car carRented;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;

    public Reservation(String reservationId, Customer customer, Car carRented, LocalDate startDate, LocalDate endDate) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.carRented = carRented;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        long daysRented = ChronoUnit.DAYS.between(startDate, endDate);
        return carRented.getPricePerDay() * daysRented;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public String getCustomerContactInfo() {
        return customer.getContactInfo();
    }

    public String getCustomerLicenseNumber() {
        return customer.getDriverLicenseNumber();
    }

    public Car getRentedCar() {
        return carRented;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
