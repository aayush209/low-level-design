package onlineshoppingsystemlikeamazon.payment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DebitCardPayment implements Payment {

    @Override
    public boolean processPayment(double amount) {
        log.info("Payment done via Debit Card  for {}", amount);
        return true;
    }
}
