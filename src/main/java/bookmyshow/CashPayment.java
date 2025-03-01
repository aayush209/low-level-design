package bookmyshow;

public class CashPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of " + amount + " using Cash.");
        return true;  // Simulating successful payment
    }
}
