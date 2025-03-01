package carrental;

public class Car {
    private String maker;
    private String model;
    private int year;
    private String licensePlate;
    private double pricePerDay;
    private boolean isAvailable;

    public Car(String maker, String model, int year, String licensePlate, double pricePerDay) {
        this.maker = maker;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean available) {
        isAvailable = available;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

}
