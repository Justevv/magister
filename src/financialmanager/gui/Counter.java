package financialmanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowPlaces.modelPlaces;

public class Counter extends JFrame {
    private JPanel contentPane = new JPanel();
    private JLabel labelGas = new JLabel("Газ:");
    public static JLabel labelElectricity = new JLabel("Электричество:");
    public static JLabel labelWater = new JLabel("Вода:");
    public static JLabel labelReadings = new JLabel("Показания:");
    public static JLabel labelPrice = new JLabel("Цена:");
    public static JLabel labelPaid = new JLabel("Оплачено:");
    public static JTextField textFieldGasReadings = new JTextField("100", 5);
    public static JTextField textFieldElectricityReadings = new JTextField("100", 5);
    public static JTextField textFieldWaterReadings = new JTextField("100", 5);
    public static JTextField textFieldGasPrice = new JTextField("100", 5);
    public static JTextField textFieldElectricityPrice = new JTextField("100", 5);
    public static JTextField textFieldWaterPrice = new JTextField("100", 5);
    public static JTextField textFieldGasPaid = new JTextField("100", 5);
    public static JTextField textFieldElectricityPaid = new JTextField("100", 5);
    public static JTextField textFieldWaterPaid = new JTextField("100", 5);
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");

    public Counter() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (WindowPlaces.action == "update") {
//            textFieldName = new JTextField(String.valueOf(WindowPlaces.model.getValueAt(WindowPlaces.selIndex, 1)), 5);
//            textFieldAddress = new JTextField(String.valueOf(WindowPlaces.model.getValueAt(WindowPlaces.selIndex, 2)), 5);
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
        c.gridx = GridBagConstraints.RELATIVE+1;
        c.gridy = 0;
        container.add(labelReadings, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy =0;
        container.add(labelPrice, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 0;
        container.add(labelPaid, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelGas, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelGas, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelGas, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelGas, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelElectricity, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelElectricity, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelElectricity, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelElectricity, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelWater, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelWater, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelWater, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelWater, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
//        container.add(textFieldAddress, c);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonOK, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
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
        if (WindowPlaces.action == "update") {
//            financialmanager.database.DbPlaces.update(textFieldName.getText(), textFieldAddress.getText(), currentId.toString());
            modelPlaces.fireTableDataChanged();
        }
        if (WindowPlaces.action == "add") {
//            financialmanager.database.DbPlaces.add(textFieldName.getText(), textFieldAddress.getText());
            modelPlaces.fireTableDataChanged();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


