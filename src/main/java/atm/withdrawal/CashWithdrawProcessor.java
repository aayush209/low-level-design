package atm.withdrawal;

import atm.ATM;

public abstract class CashWithdrawProcessor {

    protected CashWithdrawProcessor nextCashWithdrawalProcessor;

    public CashWithdrawProcessor(CashWithdrawProcessor cashWithdrawalProcessor) {
        this.nextCashWithdrawalProcessor = cashWithdrawalProcessor;
    }

    public void withdraw(ATM atm, int remainingAmount) {
        if (nextCashWithdrawalProcessor != null) {
            nextCashWithdrawalProcessor.withdraw(atm, remainingAmount);
        }
    }
}
