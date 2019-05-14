package financialmanager.gui;

import financialmanager.database.DbCounters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowCounters.modelCounters;

public class Counter extends JFrame {
    private JPanel contentPane = new JPanel();
    private JLabel labelGas = new JLabel("Газ:");
    public static JLabel labelElectricity = new JLabel("Электричество:");
    public static JLabel labelWater = new JLabel("Вода:");
    public static JLabel labelReadings = new JLabel("Показания:");
    public static JLabel labelPrice = new JLabel("Цена:");
    public static JLabel labelPaid = new JLabel("Оплачено:");
    public static JLabel labelType = new JLabel("Тип");
    public static JLabel labelDate = new JLabel("Дата");
    public static JTextField textFieldDate = new JTextField("2018-01-01", 5);
    public static JTextField textFieldGasReadings = new JTextField("1", 5);
    public static JTextField textFieldElectricityReadings = new JTextField("2", 5);
    public static JTextField textFieldWaterReadings = new JTextField("3", 5);
    public static JTextField textFieldGasPrice = new JTextField("6", 5);
    public static JTextField textFieldElectricityPrice = new JTextField("3", 5);
    public static JTextField textFieldWaterPrice = new JTextField("20", 5);
    public static JTextField textFieldGasPaid = new JTextField("7", 5);
    public static JTextField textFieldElectricityPaid = new JTextField("8", 5);
    public static JTextField textFieldWaterPaid = new JTextField("9", 5);
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");

    public Counter() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 450, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (WindowCounters.action == "update") {
            textFieldDate = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 1)), 5);
            textFieldGasReadings = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 3)), 5);
            textFieldGasPrice = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 6)), 5);
            textFieldGasPaid = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 18)), 5);
            textFieldElectricityReadings = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 4)), 5);
            textFieldElectricityPrice = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 7)), 5);
            textFieldElectricityPaid = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 19)), 5);
            textFieldWaterReadings = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 5)), 5);
            textFieldWaterPrice = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 8)), 5);
            textFieldWaterPaid = new JTextField(String.valueOf(WindowCounters.model.getValueAt(WindowCounters.selIndex, 20)), 5);
        } else {
//            textFieldName = new JTextField("", 5);
//            textFieldParentId = new JTextField("", 5);
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
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 0;
        container.add(labelType, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 0;
        container.add(labelReadings, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 0;
        container.add(labelPrice, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 0;
        container.add(labelPaid, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 1;
        container.add(labelDate, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 1;
        container.add(textFieldDate, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 2;

        container.add(labelGas, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 2;
        container.add(textFieldGasReadings, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 2;
        container.add(textFieldGasPrice, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 2;
        container.add(textFieldGasPaid, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 3;
        container.add(labelElectricity, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 3;
        container.add(textFieldElectricityReadings, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 3;
        container.add(textFieldElectricityPrice, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 3;
        container.add(textFieldElectricityPaid, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 4;
        container.add(labelWater, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 4;
        container.add(textFieldWaterReadings, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 4;
        container.add(textFieldWaterPrice, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 4;
        container.add(textFieldWaterPaid, c);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 5;
        container.add(buttonOK, c);

        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 5;
        container.add(buttonCancel, c);

    }

    public static void main(String[] args) {
        Counter app = new Counter();
        app.setVisible(true);
    }

    public static void go() {
        Counter app = new Counter();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        DbCounters dbCounters = new DbCounters();
        if (WindowCounters.action == "update") {
            dbCounters.counters.removeAll(dbCounters.counters);
            dbCounters.update(OpenWindow.userLogin, textFieldDate.getText(),
                    Float.valueOf(textFieldGasReadings.getText()), Float.valueOf(textFieldElectricityReadings.getText()), Float.valueOf(textFieldWaterReadings.getText()),
                    Float.valueOf(textFieldGasPrice.getText()), Float.valueOf(textFieldElectricityPrice.getText()), Float.valueOf(textFieldWaterPrice.getText()),
                    Float.valueOf(textFieldGasPaid.getText()), Float.valueOf(textFieldElectricityPaid.getText()), Float.valueOf(textFieldWaterPaid.getText()),
                    WindowCounters.idCounters.toString());
            dbCounters.view(OpenWindow.userLogin);
            modelCounters.fireTableDataChanged();
        }
        if (WindowCounters.action == "add") {
            dbCounters.counters.removeAll(dbCounters.counters);
            dbCounters.add(OpenWindow.userLogin, textFieldDate.getText(),
                    Float.valueOf(textFieldGasReadings.getText()), Float.valueOf(textFieldElectricityReadings.getText()), Float.valueOf(textFieldWaterReadings.getText()),
                    Float.valueOf(textFieldGasPrice.getText()), Float.valueOf(textFieldElectricityPrice.getText()), Float.valueOf(textFieldWaterPrice.getText()),
                    Float.valueOf(textFieldGasPaid.getText()), Float.valueOf(textFieldElectricityPaid.getText()), Float.valueOf(textFieldWaterPaid.getText()));
            dbCounters.view(OpenWindow.userLogin);
            modelCounters.fireTableDataChanged();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


