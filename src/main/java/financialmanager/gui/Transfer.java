package financialmanager.gui;

import financialmanager.database.DbTransfers;
import financialmanager.text.Actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowTransfers.currentId;
import static financialmanager.gui.WindowTransfers.modelTransfers;

public class Transfer extends JFrame {
    private JTextField textFieldSum = new JTextField("500", 5);
    private JComboBox comboBoxAccountSender;
    private JComboBox comboBoxAccountRecipient;
    private DbTransfers dbTransfers = new DbTransfers();

    private Transfer() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        comboBoxAccountSender = new JComboBox();
        comboBoxAccountRecipient = new JComboBox();
        dbTransfers.comboBoxTransfer(comboBoxAccountSender, comboBoxAccountRecipient);
        if (WindowTransfers.action == Actions.UPDATE) {
            comboBoxAccountSender.setSelectedItem((String.valueOf(WindowTransfers.model.getValueAt(WindowTransfers.selIndex, 1))));
            comboBoxAccountRecipient.setSelectedItem((String.valueOf(WindowTransfers.model.getValueAt(WindowTransfers.selIndex, 2))));
            textFieldSum = new JTextField(String.valueOf(WindowTransfers.model.getValueAt(WindowTransfers.selIndex, 3)), 5);
        } //else {
//            textFieldAccountSender = new JTextField("", 5);
//            textFieldAccountRecipient = new JTextField("", 5);
//            textFieldSum  = new JTextField("", 5);
//        }

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        //call onCancel() on ESCAPE
        JPanel contentPane = new JPanel();
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        JLabel labelAccountSender = new JLabel("Отправитель:");
        container.add(labelAccountSender, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        JLabel labelAccountRecipient = new JLabel("Получатель:");
        container.add(labelAccountRecipient, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        JLabel labelSum = new JLabel("Сумма:");
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

        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(e -> onOK());
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

    public static void go() {
        Transfer app = new Transfer();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        if (WindowTransfers.action == Actions.UPDATE) {
            dbTransfers.update((String) comboBoxAccountSender.getSelectedItem(), (String) comboBoxAccountRecipient.getSelectedItem(), Integer.valueOf(textFieldSum.getText()), currentId.toString(), OpenWindow.userLogin);
        }
        if (WindowTransfers.action == Actions.INSERT) {
            dbTransfers.insert((String) comboBoxAccountSender.getSelectedItem(), (String) comboBoxAccountRecipient.getSelectedItem(), Integer.valueOf(textFieldSum.getText()), OpenWindow.userLogin);
        }
        modelTransfers.setTransfers(dbTransfers.select(OpenWindow.userLogin));
        modelTransfers.fireTableDataChanged();
        setVisible(false);
    }

    private void onCancel() {
        // insert your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


