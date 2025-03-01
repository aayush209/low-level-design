package carrental;

public class Customer {
    private String name;
    private String contactInfo;
    private String driverLicenseNumber;

    public Customer(String name, String contactInfo, String driverLicenseNumber) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }
}
