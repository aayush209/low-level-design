package bookmyshow;

public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of " + amount + " using Credit Card.");
        return true;  // Simulating successful payment
    }
}
