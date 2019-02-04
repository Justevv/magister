package financialmanager.gui;

import financialmanager.businesslogic.Balance;
import financialmanager.database.DbExpenses;
import financialmanager.database.DbTransfers;

import javax.swing.*;
import java.awt.*;

import static financialmanager.database.DbExpenses.groupBalanceAccount;
import static financialmanager.database.DbTransfers.groupBalanceTransfer;

public class WindowResultAccount extends JFrame {
    private JLabel labelUser;
    private JLabel labelAccount;
    private JLabel labelProfit;
    private JLabel labelExpense;
    public static JLabel labelBalance;
    static Integer countAccount;
    private JLabel labelBalanceAccount[];
    public static JLabel labelProfitAccount[];
    public static JLabel labelExpenseAccount[];
    public static int accountNunber;

    public WindowResultAccount() {
        super("Финансовый менеджер");
        Expense.comboBoxResult();
        countAccount = Expense.comboBoxAccount.getItemCount();
        this.setBounds(100, 100, 400, countAccount * 16 + 90);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel labelBalanceAccount[] = new JLabel[countAccount];
        JLabel labelProfitAccount[] = new JLabel[countAccount];
        JLabel labelExpenseAccount[] = new JLabel[countAccount];
        accountNunber = 0;
        labelUser = new JLabel("Пользователь: " + DbExpenses.nUserSurname);
        labelAccount = new JLabel("Номер счета: " + OpenWindow.userLogin);
        labelBalance = new JLabel("Баланс: " + Balance.balance + " Рублей");
        labelProfit = new JLabel("Доход: " + DbExpenses.profit + " Рублей");
        labelExpense = new JLabel("Расход: " + DbExpenses.expense + " Рублей");
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        for (; accountNunber < countAccount; accountNunber++) {
            Object nameAccount = Expense.comboBoxAccount.getItemAt(accountNunber);
            groupBalanceAccount(OpenWindow.userLogin, String.valueOf(nameAccount));
            groupBalanceTransfer(OpenWindow.userLogin, String.valueOf(nameAccount));
            Long balance=DbExpenses.balanceCategory + DbTransfers.balanceTransiction;
            labelBalanceAccount[accountNunber] = new JLabel("Баланс счета " + nameAccount + ": " + balance + " Рублей");
            labelProfitAccount[accountNunber] = new JLabel("Доход счета " + nameAccount + ": " + DbExpenses.profitCategory + " Рублей");
            labelExpenseAccount[accountNunber] = new JLabel("Расход счета " + nameAccount + ": " + DbExpenses.expenseCategory + " Рублей");

            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = GridBagConstraints.RELATIVE;
            container.add(labelBalanceAccount[accountNunber], c);

//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 0.5;
//            c.gridx = 0;
//            c.gridy = GridBagConstraints.RELATIVE;
//            container.add(labelProfitCategory[categoryNunber], c);
//
//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 0.5;
//            c.gridx = 0;
//            c.gridy = GridBagConstraints.RELATIVE;
//            container.add(labelExpenseCategory[categoryNunber], c);
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
