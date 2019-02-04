package financialmanager.businesslogic;

import financialmanager.database.DbExpenses;

public class Balance {
    public static long balance;
    public static long profit;
    public static long expense;

    public static void start() {
        profit = DbExpenses.profit;
        expense = DbExpenses.expense;
        balance = profit - expense;
    }
}
