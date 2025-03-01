package carrental.payment;

public class UPIPaymentProcessor implements PaymentProcessor{

    @Override
    public boolean processPayment(double amount) {
        // Payment logic
        System.out.println("processing payment via UPI");
        return true;
    }
}
