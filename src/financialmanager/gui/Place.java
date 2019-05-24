package financialmanager.gui;

import financialmanager.database.DbPlaces;
import financialmanager.text.Actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowPlaces.modelPlaces;
import static financialmanager.gui.WindowPlaces.currentId;

public class Place extends JFrame {
    private JTextField textFieldName = new JTextField("Макдональдс", 5);
    private JTextField textFieldAddress = new JTextField("Есенина", 5);

    private Place() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (WindowPlaces.action == Actions.UPDATE) {
            textFieldName = new JTextField(String.valueOf(WindowPlaces.model.getValueAt(WindowPlaces.selIndex, 1)), 5);
            textFieldAddress = new JTextField(String.valueOf(WindowPlaces.model.getValueAt(WindowPlaces.selIndex, 2)), 5);
        } //else {
//            textFieldName = new JTextField("", 5);
//            textFieldParentId = new JTextField("", 5);
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
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        JLabel labelAddress = new JLabel("Адрес:");
        container.add(labelAddress, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldAddress, c);

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
        Place app = new Place();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        DbPlaces dbPlaces = new DbPlaces();
        if (WindowPlaces.action == Actions.UPDATE) {
            dbPlaces.update(textFieldName.getText(), textFieldAddress.getText(), currentId.toString());
        }
        if (WindowPlaces.action == Actions.INSERT) {
            dbPlaces.insert(textFieldName.getText(), textFieldAddress.getText());
        }
        modelPlaces.setPlaces(dbPlaces.select());
        modelPlaces.fireTableDataChanged();
        setVisible(false);
    }

    private void onCancel() {
        // insert your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


