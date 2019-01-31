package financialmanager.gui;

import financialmanager.database.DbExpenses;

import javax.swing.*;
import java.awt.*;

import static financialmanager.database.DbExpenses.groupBalanceCategory;

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
        Expense.comboBoxResult();
        countCategory = Expense.comboBoxCategory.getItemCount();
        this.setBounds(100, 100, 400, countCategory * 16 + 90);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel labelBalanceCategory[] = new JLabel[countCategory];
        JLabel labelProfitCategory[] = new JLabel[countCategory];
        JLabel labelExpenseCategory[] = new JLabel[countCategory];
        categoryNunber = 0;
        labelUser = new JLabel("Пользователь: " + DbExpenses.nUserSurname);
        labelAccount = new JLabel("Номер счета: " + OpenWindow.userLogin);
        labelBalance = new JLabel("Баланс: " + DbExpenses.balance + " Рублей");
        labelProfit = new JLabel("Доход: " + DbExpenses.profit + " Рублей");
        labelExpense = new JLabel("Расход: " + DbExpenses.expense + " Рублей");
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        for (; categoryNunber < countCategory; categoryNunber++) {
            Object nameCategory = Expense.comboBoxCategory.getItemAt(categoryNunber);
            groupBalanceCategory(OpenWindow.userLogin, String.valueOf(nameCategory));
            labelBalanceCategory[categoryNunber] = new JLabel("Баланс категории " + nameCategory + ": " + DbExpenses.balanceCategory + " Рублей");
            labelProfitCategory[categoryNunber] = new JLabel("Доход категории " + nameCategory + ": " + DbExpenses.profitCategory + " Рублей");
            labelExpenseCategory[categoryNunber] = new JLabel("Расход категории " + nameCategory + ": " + DbExpenses.expenseCategory + " Рублей");

//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 0.5;
//            c.gridx = 0;
//            c.gridy = GridBagConstraints.RELATIVE;
//            container.add(labelBalanceCategory[categoryNunber], c);

//            c.fill = GridBagConstraints.HORIZONTAL;
//            c.weightx = 0.5;
//            c.gridx = 0;
//            c.gridy = GridBagConstraints.RELATIVE;
//            container.add(labelProfitCategory[categoryNunber], c);
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
