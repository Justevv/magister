package financialmanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateUser extends JFrame {
    private JPanel contentPane = new JPanel();
    private JLabel labelSurname = new JLabel("Фамилия:");
    private JLabel labelName = new JLabel("Имя:");
    public static JLabel labelBirthday = new JLabel("Дата рождения:");
    private JLabel labelPhone = new JLabel("Телефон:");
    private JLabel labelSex = new JLabel("Пол:");
    private JLabel labelEmail = new JLabel("Email:");
    public static JTextField textFieldSurname;
    public static JTextField textFieldName;
    public static JTextField textFieldBirthday;
    public static JTextField textFieldSex;
    public static JTextField textFieldPhone;
    public static JTextField textFieldEmail;
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");

    public UpdateUser() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textFieldSurname = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 1)), 5);
        textFieldName = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 2)), 5);
        textFieldBirthday = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 3)), 5);
        textFieldSex = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 4)), 5);
        textFieldPhone = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 5)), 5);
        textFieldEmail = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 6)), 5);

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
        container.add(labelSurname, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelBirthday, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelSex, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelPhone, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelEmail, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldSurname, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldBirthday, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldSex, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldPhone, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldEmail, c);

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
        UpdateUser app = new UpdateUser();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        financialmanager.database.UpdateUser.main();
    }

    private void onCancel() {
        // add your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


