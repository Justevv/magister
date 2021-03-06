package financialmanager.gui;

import financialmanager.database.DbAccounts;
import financialmanager.text.Actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowAccounts.currentId;
import static financialmanager.gui.WindowAccounts.modelAccounts;

public class Account extends JFrame {
    private JTextField textFieldName = new JTextField("Свет", 5);

    private Account() {
        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (WindowAccounts.action == Actions.UPDATE) {
            textFieldName = new JTextField(String.valueOf(WindowAccounts.model.getValueAt(WindowAccounts.selIndex, 1)), 5);
        } //else {
//            textFieldName = new JTextField("", 5);
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
        JLabel labelName = new JLabel("Имя:");
        container.add(labelName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldName, c);

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
        Account app = new Account();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        String Name = textFieldName.getText();
        DbAccounts dbAccounts = new DbAccounts();
        if (WindowAccounts.action == Actions.UPDATE){
            dbAccounts.update(Name, currentId.toString());
        }
        if (WindowAccounts.action == Actions.INSERT){
            dbAccounts.insert(Name);
        }
        modelAccounts.setAccounts(dbAccounts.select());
        modelAccounts.fireTableDataChanged();
        setVisible(false);
    }

    private void onCancel() {
        // insert your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


