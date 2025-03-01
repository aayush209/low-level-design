package atm;

public class UserBankAccount {

    private int balance;

    public void withdrawalBalance(int amount) {
        balance = balance - amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int initialAmount) {
        balance = initialAmount;
    }
}
