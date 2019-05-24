package financialmanager.gui;

import financialmanager.businesslogic.Balance;
import financialmanager.database.DbExpenses;
import financialmanager.database.DbTransfers;

import javax.swing.*;
import java.awt.*;

public class WindowResultAccount extends JFrame {

    private WindowResultAccount() {
        super("Финансовый менеджер");
        Expense expenseData = new Expense();
        Expense.comboBoxResult();
        int countAccount = expenseData.comboBoxAccount.getItemCount();
        this.setBounds(100, 100, 400, countAccount * 16 + 90);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel[] labelBalanceAccount = new JLabel[countAccount];
//        JLabel labelProfitAccount[] = new JLabel[countAccount];
//        JLabel labelExpenseAccount[] = new JLabel[countAccount];
        int accountNumber = 0;
        DbExpenses dbExpenses = new DbExpenses();
        long profit = dbExpenses.getProfit(OpenWindow.userLogin);
        long expense = dbExpenses.getExpense(OpenWindow.userLogin);
        Balance balance = new Balance();
//        JLabel labelUser = new JLabel("Пользователь: " + dbExpenses.userSurname);
//        JLabel  labelAccount = new JLabel("Номер счета: " + OpenWindow.userLogin);
        JLabel labelBalance = new JLabel("Баланс: " + balance.getBalance(profit, expense) + " Рублей");
        JLabel labelProfit = new JLabel("Доход: " + profit + " Рублей");
        JLabel labelExpense = new JLabel("Расход: " + expense + " Рублей");
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        for (; accountNumber < countAccount; accountNumber++) {
            Object nameAccount = expenseData.comboBoxAccount.getItemAt(accountNumber);
            dbExpenses.groupBalanceAccount(OpenWindow.userLogin, String.valueOf(nameAccount));
            DbTransfers dbTransfers = new DbTransfers();
            dbTransfers.groupBalanceTransfer(OpenWindow.userLogin, String.valueOf(nameAccount));
            long balanceC = dbExpenses.balanceCategory + dbTransfers.balanceTransaction;
            labelBalanceAccount[accountNumber] = new JLabel("Баланс счета " + nameAccount + ": " + balanceC + " Рублей");
//            labelProfitAccount[accountNunber] = new JLabel("Доход счета " + nameAccount + ": " + dbExpenses.profitCategory + " Рублей");
//            labelExpenseAccount[accountNunber] = new JLabel("Расход счета " + nameAccount + ": " + dbExpenses.expenseCategory + " Рублей");

            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = GridBagConstraints.RELATIVE;
            container.add(labelBalanceAccount[accountNumber], c);

//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 0.5;
//            c.gridx = 0;
//            c.gridy = GridBagConstraints.RELATIVE;
//            container.insert(labelProfitCategory[categoryNunber], c);
//
//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 0.5;
//            c.gridx = 0;
//            c.gridy = GridBagConstraints.RELATIVE;
//            container.insert(labelExpenseCategory[categoryNunber], c);
        }

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelBalance, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelProfit, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelExpense, c);
    }

    public static void go() {
        WindowResultAccount app = new WindowResultAccount();
        app.setVisible(true);
    }
}
