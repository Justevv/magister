package financialmanager.gui;

import financialmanager.database.DbExpenses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Expenses extends JFrame {
    private JPanel contentPane = new JPanel();
    private JLabel labelDate = new JLabel("Дата:");
    private JLabel labelCategory = new JLabel("Категория:");
    private JLabel labelPlace = new JLabel("Место:");
    private JLabel labelPaymentType = new JLabel("Тип оплаты:");
    private JLabel labelSum = new JLabel("Сумма:");
    private JTextField textFieldDate = new JTextField("20181010", 5);
    public static JComboBox comboBoxCategory;
    public static JComboBox comboBoxPlace;
    public static JComboBox comboBoxPaymentType;
    private JTextField textFieldSum = new JTextField("100", 5);
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");
    public static int filternId = 50000;

    public Expenses() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldDate, c);

        // comboBoxCategory = new JComboBox(DbExpenses.items);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxCategory, c);

        //  comboBoxPaymentType = new JComboBox(DbExpenses.items);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxPaymentType, c);

        // comboBoxPlace = new JComboBox();
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

        buttonOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                onOK();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        container.add(buttonOK, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 5;
        container.add(buttonCancel, c);
    }

    public static void main(String[] args) {
        Expenses app = new Expenses();
        app.setVisible(true);
    }

    public static void go() {
        Expenses app = new Expenses();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {

        financialmanager.database.Expenses.main();
              DbExpenses.balance = DbExpenses.balance + new Integer(textFieldSum.getText());
        WindowExpenses.labelBalance.setText("Баланс: " + DbExpenses.balance + " Рублей");
    }

    private void onCancel() {
        // add your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}
