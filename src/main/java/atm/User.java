package atm;

public class User {

    private Card card;
    private UserBankAccount bankAccount;

    public Card getCard() {
        return card;
    }

    public UserBankAccount getBankAccount() {
        return bankAccount;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
