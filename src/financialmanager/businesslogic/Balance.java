package financialmanager.businesslogic;


public class Balance {

    public long getBalance(long profit, long expense) {
        long balance = profit - expense;
        return balance;
    }
}
