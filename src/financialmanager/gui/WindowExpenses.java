package financialmanager.gui;

import financialmanager.businesslogic.Balance;
import financialmanager.database.DbExpenses;
import financialmanager.table.ExpensesTable;
import financialmanager.text.Actions;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowExpenses extends JFrame {
    static JLabel labelProfit = new JLabel("Номер счета:");
    static JLabel labelExpense = new JLabel("Номер счета:");
    static JLabel labelBalance;
    private static DbExpenses dbExpenses = new DbExpenses();
    static ExpensesTable modelExpenses = new ExpensesTable(dbExpenses.select(OpenWindow.userLogin));
    private static int[] selectedRows;
    private static int[] selectedColumns;
    private int i;
    private JTable jTabExpenses = new JTable(modelExpenses);
    static TableModel model;
    static int selIndex;
    static Object value;
    private Object Sum;
    static Actions action;
    static JComboBox comboBoxCategory;

    private WindowExpenses() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        long profit = dbExpenses.getProfit(OpenWindow.userLogin);
        long expense = dbExpenses.getExpense(OpenWindow.userLogin);
        Balance balance = new Balance();
        JLabel labelUser = new JLabel("Пользователь: " + dbExpenses.userSurname);
        JLabel labelAccount = new JLabel("Номер счета: " + OpenWindow.userLogin);
        labelBalance = new JLabel("Баланс: " + balance.getBalance(profit, expense) + " Рублей");
        labelProfit = new JLabel("Доход: " + profit + " Рублей");
        labelExpense = new JLabel("Расход: " + expense + " Рублей");
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelUser, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelAccount, c);

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

        JFrame jfrm = new JFrame("JTableExample");
        modelExpenses = new ExpensesTable(dbExpenses.select(OpenWindow.userLogin));
        //На основе модели, создадим новую JTable
        jTabExpenses = new JTable(modelExpenses);
        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        JScrollPane jscrlp = new JScrollPane(jTabExpenses);
        //Устаналиваем размеры прокручиваемой области
        jTabExpenses.setPreferredScrollableViewportSize(new Dimension(550, 350));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

        ListSelectionModel selModel = jTabExpenses.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectedRows = jTabExpenses.getSelectedRows();
                selectedColumns = jTabExpenses.getSelectedColumns();
                for (i = 0; i < selectedRows.length; i++) {
                    selIndex = selectedRows[i];
                    model = jTabExpenses.getModel();
                    value = model.getValueAt(selIndex, 0);
                    Sum = model.getValueAt(selIndex, 6);
                }
            }
        });

        JButton buttonAddUser = new JButton("Пользователи");
        buttonAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowUsers.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddUser, c);

        JButton buttonAddCategory = new JButton("Категории");
        buttonAddCategory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowCategories.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddCategory, c);

        JButton buttonAddPlace = new JButton("Места");
        buttonAddPlace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowPlaces.go();
            }
        });
        jfrm.add(buttonAddPlace);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddPlace, c);

        JButton buttonAccount = new JButton("Счета");
        buttonAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowAccounts.go();
            }
        });
        jfrm.add(buttonAccount);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAccount, c);

        JButton buttonTransfer = new JButton("Переводы");
        buttonTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowTransfers.go();
            }
        });
        jfrm.add(buttonTransfer);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonTransfer, c);

        JButton buttonCounters = new JButton("Счетчики");
        buttonCounters.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowCounters.go();
            }
        });
        jfrm.add(buttonCounters);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonCounters, c);

        JButton buttonResultAccount = new JButton("Итоги по счетам");
        buttonResultAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowResultAccount.go();
            }
        });
        jfrm.add(buttonResultAccount);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonResultAccount, c);

        JButton buttonResultCategory = new JButton("Итоги по категориям");
        buttonResultCategory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WindowResultCategory.go();
            }
        });
        jfrm.add(buttonResultCategory);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonResultCategory, c);

        JButton buttonAdd = new JButton("Добавить запись");
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action = Actions.INSERT;
                Expense.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAdd, c);

        JButton buttonUpdate = new JButton("Редактировать запись");
        buttonUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action = Actions.UPDATE;
                Expense.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdate, c);

        JButton buttonDelete = new JButton("Удалить запись");
        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dbExpenses.delete(value);
                modelExpenses.setExpenses(dbExpenses.select(OpenWindow.userLogin));
                modelExpenses.fireTableDataChanged();
                dbExpenses.select(OpenWindow.userLogin);
                long profit = dbExpenses.getProfit(OpenWindow.userLogin);
                long expense = dbExpenses.getExpense(OpenWindow.userLogin);
                labelBalance.setText("Баланс: " + balance.getBalance(profit, expense) + " Рублей");
                labelProfit.setText("Доход: " + profit + " Рублей");
                labelExpense.setText("Расход: " + expense + " Рублей");
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDelete, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = GridBagConstraints.RELATIVE + 1;
        c.gridx = 1;
        c.gridy = 0;
        container.add(jscrlp, c);

    }

    public static void go() {
        WindowExpenses app = new WindowExpenses();
        app.setVisible(true);
    }
}
