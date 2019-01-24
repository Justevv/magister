package financialmanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdatePlace extends JFrame {
    private JPanel contentPane = new JPanel();
    private JLabel labelName = new JLabel("Имя:");
    public static JLabel labelAddress = new JLabel("Адрес:");
    public static JTextField textFieldName;
    public static JTextField textFieldAddress;
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");

    public UpdatePlace() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textFieldName = new JTextField(String.valueOf(WindowPlaces.model.getValueAt(WindowPlaces.selIndex, 1)), 5);
        textFieldAddress = new JTextField(String.valueOf(WindowPlaces.model.getValueAt(WindowPlaces.selIndex, 2)), 5);

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
        container.add(labelName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
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
        UpdatePlace app = new UpdatePlace();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        financialmanager.database.DbPlaces.update();
    }

    private void onCancel() {
        // add your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


