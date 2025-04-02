package bookmyshow;

public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount) {
        // Simulating successful payment
        System.out.println("Processing credit card payment for amount: $" + amount);
        return true;  // Always return true for demo
    }
}
