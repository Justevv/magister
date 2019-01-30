package financialmanager.gui;

import financialmanager.database.DbExpenses;

import javax.swing.*;
import java.awt.*;

import static financialmanager.database.DbExpenses.groupBalanceAccount;

public class WindowResult extends JFrame {
    private JLabel labelUser;
    private JLabel labelAccount;
    private JLabel labelProfit;
    private JLabel labelExpense;
    public static JLabel labelBalance;
    static Integer countAccount = Expense.comboBoxAccount.getItemCount();
    private JLabel labelBalanceAccount[] = new JLabel[countAccount];
    public static JLabel labelProfitAccount[] = new JLabel[countAccount];
    public static JLabel labelExpenseAccount[] = new JLabel[countAccount];
    public static int accountNunber;

    public WindowResult() {


        super("Финансовый менеджер");
        this.setBounds(100, 100, 400, countAccount*35);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        DbExpenses.view(OpenWindow.userLogin);
//        DbExpenses.comboBoxRead();
        accountNunber=0;
        labelUser = new JLabel("Пользователь: " + DbExpenses.nUserSurname);
        labelAccount = new JLabel("Номер счета: " + OpenWindow.userLogin);
        labelBalance = new JLabel("Баланс: " + DbExpenses.balance + " Рублей");
        labelProfit = new JLabel("Доход: " + DbExpenses.profit + " Рублей");
        labelExpense = new JLabel("Расход: " + DbExpenses.expense + " Рублей");
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        for (; accountNunber < countAccount; accountNunber++) {
            Object nameAccount = Expense.comboBoxAccount.getItemAt(accountNunber);
            groupBalanceAccount(OpenWindow.userLogin, accountNunber + 1);
            labelBalanceAccount[accountNunber] = new JLabel("Баланс счета " + nameAccount + ": " + DbExpenses.balanceCategory + " Рублей");
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
//            container.add(labelProfitAccount[accountNunber], c);
//
//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 0.5;
//            c.gridx = 0;
//            c.gridy = GridBagConstraints.RELATIVE;
//            container.add(labelExpenseAccount[accountNunber], c);
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

    public static void main(String[] args) {
        WindowResult app = new WindowResult();
        app.setVisible(true);
    }

    public static void go() {
        WindowResult app = new WindowResult();
        app.setVisible(true);
    }
}
