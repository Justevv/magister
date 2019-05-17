package financialmanager.gui;

import financialmanager.businesslogic.Balance;
import financialmanager.database.DbExpenses;

import javax.swing.*;
import java.awt.*;


public class WindowResultCategory extends JFrame {
    private JLabel labelUser;
    private JLabel labelAccount;
    private JLabel labelProfit;
    private JLabel labelExpense;
    public static JLabel labelBalance;
    static Integer countCategory;
    private JLabel labelBalanceCategory[];
    public static JLabel labelProfitCategory[];
    public static JLabel labelExpenseCategory[];
    public static int categoryNunber;

    public WindowResultCategory() {
        super("Финансовый менеджер");
        Expense expenseData = new Expense();
        Expense.comboBoxResult();
        countCategory = expenseData.comboBoxCategory.getItemCount();
        this.setBounds(100, 100, 400, countCategory * 16 + 90);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel labelBalanceCategory[] = new JLabel[countCategory];
        JLabel labelProfitCategory[] = new JLabel[countCategory];
        JLabel labelExpenseCategory[] = new JLabel[countCategory];
        categoryNunber = 0;
        DbExpenses dbExpenses = new DbExpenses();
        long profit = dbExpenses.getProfit(OpenWindow.userLogin);
        long expense = dbExpenses.getExpense(OpenWindow.userLogin);
        Balance balance = new Balance();
        labelUser = new JLabel("Пользователь: " + dbExpenses.userSurname);
        labelAccount = new JLabel("Номер счета: " + OpenWindow.userLogin);
        labelBalance = new JLabel("Баланс: " + balance.getBalance(profit, expense) + " Рублей");
        labelProfit = new JLabel("Доход: " + profit + " Рублей");
        labelExpense = new JLabel("Расход: " + expense + " Рублей");
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        for (; categoryNunber < countCategory; categoryNunber++) {
            Object nameCategory = expenseData.comboBoxCategory.getItemAt(categoryNunber);
            dbExpenses.groupBalanceCategory(OpenWindow.userLogin, String.valueOf(nameCategory));
            labelBalanceCategory[categoryNunber] = new JLabel("Баланс категории " + nameCategory + ": " + dbExpenses.balanceCategory + " Рублей");
            labelProfitCategory[categoryNunber] = new JLabel("Доход категории " + nameCategory + ": " + dbExpenses.profitCategory + " Рублей");
            labelExpenseCategory[categoryNunber] = new JLabel("Расход категории " + nameCategory + ": " + dbExpenses.expenseCategory + " Рублей");

//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 0.5;
//            c.gridx = 0;
//            c.gridy = GridBagConstraints.RELATIVE;
//            container.insert(labelBalanceCategory[categoryNunber], c);

//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 0.5;
//            c.gridx = 0;
//            c.gridy = GridBagConstraints.RELATIVE;
//            container.insert(labelProfitCategory[categoryNunber], c);
//
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = GridBagConstraints.RELATIVE;
            container.add(labelExpenseCategory[categoryNunber], c);
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
        WindowResultCategory app = new WindowResultCategory();
        app.setVisible(true);
    }
}
