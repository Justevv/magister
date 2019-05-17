package financialmanager.gui;

import financialmanager.businesslogic.Balance;
import financialmanager.database.DbExpenses;
import financialmanager.text.Actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowExpenses.modelExpenses;
import static financialmanager.gui.WindowExpenses.value;

public class Expense extends JFrame {
    private JPanel contentPane = new JPanel();
    private JLabel labelDate = new JLabel("Дата:");
    private JLabel labelCategory = new JLabel("Категория:");
    private JLabel labelPlace = new JLabel("Место:");
    private JLabel labelPaymentType = new JLabel("Тип оплаты:");
    private JLabel labelSum = new JLabel("Сумма:");
    private JLabel labelAccount = new JLabel("Счет:");
    private JLabel labelTransactionType = new JLabel("Тип платежа:");
    public static JTextField textFieldDate = new JTextField("20181010", 5);
    public static JTextField textFieldSum = new JTextField("100", 5);
    public static JComboBox comboBoxCategory;
    public static JComboBox comboBoxPlace;
    public static JComboBox comboBoxPaymentType;
    public static JComboBox comboBoxAccount;
    public static JComboBox comboBoxTransactionType;
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");
    DbExpenses dbExpenses = new DbExpenses();

    public Expense() {
        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        comboBoxPlace = new JComboBox();
        comboBoxPaymentType = new JComboBox();
        comboBoxCategory = new JComboBox();
        comboBoxAccount = new JComboBox();
        comboBoxTransactionType = new JComboBox();
        if (WindowExpenses.action == null) {
            WindowExpenses.comboBoxCategory = new JComboBox();
        }
        dbExpenses.comboBoxRead(comboBoxPlace, comboBoxPaymentType, comboBoxCategory, comboBoxAccount, comboBoxTransactionType);

        if (WindowExpenses.action == Actions.UPDATE) {
            comboBoxCategory.setSelectedItem((String.valueOf(WindowExpenses.model.getValueAt(WindowExpenses.selIndex, 3))));
            comboBoxPlace.setSelectedItem((String.valueOf(WindowExpenses.model.getValueAt(WindowExpenses.selIndex, 4))));
            comboBoxPaymentType.setSelectedItem((String.valueOf(WindowExpenses.model.getValueAt(WindowExpenses.selIndex, 5))));
            comboBoxAccount.setSelectedItem((String.valueOf(WindowExpenses.model.getValueAt(WindowExpenses.selIndex, 7))));
            comboBoxTransactionType.setSelectedItem((String.valueOf(WindowExpenses.model.getValueAt(WindowExpenses.selIndex, 8))));
            textFieldDate = new JTextField(String.valueOf(WindowExpenses.model.getValueAt(WindowExpenses.selIndex, 1)), 5);
            textFieldSum = new JTextField(String.valueOf(WindowExpenses.model.getValueAt(WindowExpenses.selIndex, 6)), 5);
        } else {
//            textFieldDate = new JTextField("", 5);
//            textFieldSum = new JTextField("", 5);
        }
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        //call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelDate, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelCategory, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelPaymentType, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelPlace, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelSum, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelAccount, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelTransactionType, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldDate, c);

        // textFieldBirthday = new JComboBox(DbExpenses.items);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxCategory, c);

        //  textFieldEmail = new JComboBox(DbExpenses.items);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxPaymentType, c);

        // textFieldPhone = new JComboBox();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxPlace, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldSum, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxAccount, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxTransactionType, c);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 7;
        container.add(buttonOK, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 7;
        container.add(buttonCancel, c);
    }

    public static void main(String[] args) {
        Expense app = new Expense();
        app.setVisible(true);
    }

    public static void go() {
        Expense app = new Expense();
        // app.pack();
        app.setVisible(true);
    }

    public static void comboBoxResult() {
        new Expense();
        // app.pack();
    }

    private void onOK() {
        dbExpenses.expenses.removeAll(dbExpenses.expenses);
        if (WindowExpenses.action == Actions.UPDATE) {
            dbExpenses.update(OpenWindow.userLogin,
                    (String) comboBoxPlace.getSelectedItem(),
                    (String) comboBoxPaymentType.getSelectedItem(),
                    (String) comboBoxCategory.getSelectedItem(),
                    (String) comboBoxAccount.getSelectedItem(),
                    (String) comboBoxTransactionType.getSelectedItem(),
                    textFieldDate.getText(),
                    new Integer(textFieldSum.getText()),
                    value);
        }
        if (WindowExpenses.action == Actions.INSERT) {
            dbExpenses.insert(OpenWindow.userLogin,
                    (String) comboBoxPlace.getSelectedItem(),
                    (String) comboBoxPaymentType.getSelectedItem(),
                    (String) comboBoxCategory.getSelectedItem(),
                    (String) comboBoxAccount.getSelectedItem(),
                    (String) comboBoxTransactionType.getSelectedItem(),
                    textFieldDate.getText(),
                    new Integer(textFieldSum.getText()));
        }
        dbExpenses.select(OpenWindow.userLogin);
        modelExpenses.fireTableDataChanged();
        DbExpenses dbExpenses = new DbExpenses();
        long profit = dbExpenses.profit;
        long expense = dbExpenses.expense;
        Balance balance = new Balance();
        balance.getBalance(profit, expense);
        WindowExpenses.labelBalance.setText("Баланс: " +
                balance.getBalance(profit, expense) + " Рублей");
    }

    private void onCancel() {
        // insert your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}
