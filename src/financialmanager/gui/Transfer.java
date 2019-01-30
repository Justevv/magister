package financialmanager.gui;

import financialmanager.database.DbTransfers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowTransfers.currentId;
import static financialmanager.gui.WindowTransfers.modelTransfers;

public class Transfer extends JFrame {
    private JPanel contentPane = new JPanel();
    public static JLabel labelAccountSender = new JLabel("Отправитель:");
    public static JLabel labelAccountRecipient = new JLabel("Получатель:");
    public static JLabel labelSum = new JLabel("Сумма:");
    public static JTextField textFieldAccountSender = new JTextField("2", 5);
    public static JTextField textFieldAccountRecipient = new JTextField("3", 5);
    public static JTextField textFieldSum = new JTextField("500", 5);
    public static JComboBox comboBoxAccountSender;
    public static JComboBox comboBoxAccountRecipient;
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");

    public Transfer() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        comboBoxAccountSender = new JComboBox();
        comboBoxAccountRecipient = new JComboBox();
        DbTransfers.comboBoxTransfer(comboBoxAccountSender, comboBoxAccountRecipient);
        if (WindowTransfers.action == "update") {
            comboBoxAccountSender.setSelectedItem((String.valueOf(WindowTransfers.model.getValueAt(WindowTransfers.selIndex, 1))));
            comboBoxAccountRecipient.setSelectedItem((String.valueOf(WindowTransfers.model.getValueAt(WindowTransfers.selIndex, 2))));
            textFieldSum = new JTextField(String.valueOf(WindowTransfers.model.getValueAt(WindowTransfers.selIndex, 3)), 5);
        } else {
//            textFieldAccountSender = new JTextField("", 5);
//            textFieldAccountRecipient = new JTextField("", 5);
//            textFieldSum  = new JTextField("", 5);
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
        container.add(labelAccountSender, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelAccountRecipient, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelSum, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxAccountSender, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxAccountRecipient, c);

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
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonOK, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonCancel, c);

    }

    public static void main(String[] args) {
        Expense app = new Expense();
        app.setVisible(true);
    }

    public static void go() {
        Transfer app = new Transfer();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        if (WindowTransfers.action == "update") {
            financialmanager.database.DbTransfers.update((String)comboBoxAccountSender.getSelectedItem(), (String)comboBoxAccountRecipient.getSelectedItem(),Integer.valueOf(textFieldSum.getText()), currentId.toString());
            modelTransfers.fireTableDataChanged();
        }
        if (WindowTransfers.action == "add") {
            financialmanager.database.DbTransfers.add((String)comboBoxAccountSender.getSelectedItem(), (String)comboBoxAccountRecipient.getSelectedItem(),Integer.valueOf(textFieldSum.getText()),OpenWindow.userLogin);
            modelTransfers.fireTableDataChanged();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


