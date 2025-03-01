package carrental.payment;

public interface PaymentProcessor {

    boolean processPayment(double amount);
}
