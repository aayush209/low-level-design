package carrental.payment;

public class CreditCardPaymentProcessor implements PaymentProcessor{

    @Override
    public boolean processPayment(double amount) {
        // Payment logic
        System.out.println("processing payment via credit card");
        return true;
    }
}
