package financialmanager.gui;

import financialmanager.database.DbUsers;
import financialmanager.text.Actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowUsers.modelUsers;
import static financialmanager.gui.WindowUsers.currentId;

public class User extends JFrame {
    private JTextField textFieldSurname = new JTextField("Коровин", 5);
    private JTextField textFieldName = new JTextField("Михаил", 5);
    private JTextField textFieldBirthday = new JTextField("20010101", 5);
    private JTextField textFieldPhone = new JTextField("+79203336699", 5);
    private JTextField textFieldSex = new JTextField("M", 5);
    private JTextField textFieldEmail = new JTextField("korovin@mail.ru", 5);

    private User() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (WindowUsers.action == Actions.UPDATE) {
            textFieldSurname = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 1)), 5);
            textFieldName = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 2)), 5);
            textFieldBirthday = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 3)), 5);
            textFieldSex = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 4)), 5);
            textFieldPhone = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 5)), 5);
            textFieldEmail = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 6)), 5);
        } //else {
//            textFieldSurname = new JTextField("", 5);
//            textFieldName = new JTextField("", 5);
//            textFieldBirthday = new JTextField("", 5);
//            textFieldSex = new JTextField("", 5);
//            textFieldPhone = new JTextField("", 5);
//            textFieldEmail = new JTextField("", 5);
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
        JLabel labelSurname = new JLabel("Фамилия:");
        container.add(labelSurname, c);

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
        JLabel labelBirthday = new JLabel("Дата рождения:");
        container.add(labelBirthday, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        JLabel labelSex = new JLabel("Пол:");
        container.add(labelSex, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        JLabel labelPhone = new JLabel("Телефон:");
        container.add(labelPhone, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        JLabel labelEmail = new JLabel("Email:");
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
        User app = new User();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        DbUsers dbUsers = new DbUsers();
        String Surname = textFieldSurname.getText();
        String Name = textFieldName.getText();
        String Birthday = textFieldBirthday.getText();
        String Sex = textFieldSex.getText();
        String Phone = textFieldPhone.getText();
        String Email = textFieldEmail.getText();
        if (WindowUsers.action == Actions.UPDATE) {
            dbUsers.update(Surname, Name, Birthday, Sex, Phone, Email, currentId.toString(), WindowUsers.currentEmail.toString());
        }
        if (WindowUsers.action == Actions.INSERT) {
            dbUsers.insert(Surname, Name, Birthday, Sex, Phone, Email);
        }
        modelUsers.setUsers(dbUsers.select());
        modelUsers.fireTableDataChanged();
        setVisible(false);
    }

    private void onCancel() {
        // insert your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


