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
    private JButton buttonResultAccount = new JButton("Итоги по счетам");
    private JButton buttonResultCategory = new JButton("Итоги по категориям");
    private JButton button = new JButton("Press");
    private JButton buttonAdd = new JButton("Добавить запись");
    private JButton buttonDelete = new JButton("Удалить запись");
    private JButton buttonUpdate = new JButton("Редактировать запись");
    private JButton buttonAddUser = new JButton("Пользователи");
    private JButton buttonAddCategory = new JButton("Категории");
    private JButton buttonAccount = new JButton("Счета");
    private JButton buttonTransfer = new JButton("Переводы");
    private JButton buttonAddPlace = new JButton("Места");
    private JButton buttonCounters = new JButton("Счетчики");
    private JTextField input = new JTextField("", 5);
    private JLabel label = new JLabel("Input:");
    private JLabel labelUser;
    private JLabel labelAccount = new JLabel("Номер счета:");
    static JLabel labelProfit = new JLabel("Номер счета:");
    static JLabel labelExpense = new JLabel("Номер счета:");
    private JLabel labelBalanceCategory;
    public static JLabel labelBalance;
    private JRadioButton radio1 = new JRadioButton("Select this");
    private JRadioButton radio2 = new JRadioButton("Select that");
    private JRadioButton radio3 = new JRadioButton("Select no that");
    private JCheckBox check = new JCheckBox("Check", false);
    static DbExpenses dbExpenses = new DbExpenses();
    public static ExpensesTable modelExpenses = new ExpensesTable(dbExpenses.select(OpenWindow.userLogin));
    public static int[] selectedRows;
    public static int[] selectedColumns;
    public static int i;
    private static JTable jTabExpenses = new JTable(modelExpenses);
    public static TableModel model;
    public static int selIndex;
    public static Object value;
    public static Object Sum;
    public static Actions action;
    public static JComboBox comboBoxCategory;

    public WindowExpenses() {


        super("Финансовый менеджер");
        this.setBounds(100, 100, 800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dbExpenses.select(OpenWindow.userLogin);

        long profit = dbExpenses.getProfit(OpenWindow.userLogin);
        long expense = dbExpenses.getExpense(OpenWindow.userLogin);
        Balance balance = new Balance();
        balance.getBalance(profit, expense);

        labelUser = new JLabel("Пользователь: " + dbExpenses.userSurname);
        labelAccount = new JLabel("Номер счета: " + OpenWindow.userLogin);
        labelBalance = new JLabel("Баланс: " + balance.getBalance(profit, expense) + " Рублей");
        labelProfit = new JLabel("Доход: " + profit + " Рублей");
        labelExpense = new JLabel("Расход: " + expense + " Рублей");
        labelBalanceCategory = new JLabel("Итог категории: " + dbExpenses.profitCategory + " Рублей");
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = 0;
//        container.insert(label, c);
        //    container.insert(label);

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
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 1;
//        c.gridy = 0;
//        container.insert(input, c);
//      //  container.insert(input);

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

//        ButtonGroup group = new ButtonGroup();
//        group.insert(radio1);
//        group.insert(radio2);
//        group.insert(radio3);

//        if (shouldWeightX) {
//            c.weightx = 0.5;
//        }
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 1;
//        container.insert(radio1, c);
//        //container.insert(radio1);

//        radio1.setSelected(true);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 1;
//        c.gridy = 1;
//        container.insert(radio2, c);
//       // container.insert(radio2);

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 2;
//        c.gridy = 1;
//        container.insert(radio3, c);
//      //  container.insert(radio3);

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = 2;
//        container.insert(check, c);
//      //  container.insert(check);

//        button.addActionListener(new ButtonEventListener());
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = 3;
//        container.insert(button, c);

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
        //container.insert(jscrlp);

//        ActionListener actionListener = new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                DbExpenses.balanceCategory(OpenWindow.userLogin, (String) comboBoxCategory.getSelectedItem());
//                labelBalanceCategory.setText("Итог категории " + comboBoxCategory.getSelectedItem() + ": " + DbExpenses.profitCategory + " Рублей");
//            }
//        };
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = GridBagConstraints.RELATIVE;
//        container.insert(comboBoxCategory, c);
//        comboBoxCategory.addActionListener(actionListener);

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 1;
//        c.gridy = GridBagConstraints.RELATIVE - 1;
//        container.insert(labelBalanceCategory, c);
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = "";
            message += "Button was pressed\n";
            message += "Actions is " + input.getText() + "\n";
            message += (radio1.isSelected() ? "Radio #1" : "Radio #2")
                    + " is selected\n";
            message += "CheckBox is " + ((check.isSelected())
                    ? "checked" : "unchecked");
            JOptionPane.showMessageDialog(null,
                    message,
                    "Output",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void go() {
        WindowExpenses app = new WindowExpenses();
        app.setVisible(true);
    }
}
