package financialmanager.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowUsers.modelUsers;
import static financialmanager.gui.WindowUsers.currentId;

public class User extends JFrame {
    private JPanel contentPane = new JPanel();
    private JLabel labelSurname = new JLabel("Фамилия:");
    private JLabel labelName = new JLabel("Имя:");
    public static JLabel labelBirthday = new JLabel("Дата рождения:");
    private JLabel labelPhone = new JLabel("Телефон:");
    private JLabel labelSex = new JLabel("Пол:");
    private JLabel labelEmail = new JLabel("Email:");
    public static JTextField textFieldSurname = new JTextField("Коровин", 5);
    public static JTextField textFieldName = new JTextField("Михаил", 5);
    public static JTextField textFieldBirthday = new JTextField("20010101", 5);
    public static JTextField textFieldPhone = new JTextField("+79203336699", 5);
    public static JTextField textFieldSex = new JTextField("M", 5);
    public static JTextField textFieldEmail = new JTextField("korovin@mail.ru", 5);
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");

    public User() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (WindowUsers.action == "update") {
            textFieldSurname = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 1)), 5);
            textFieldName = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 2)), 5);
            textFieldBirthday = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 3)), 5);
            textFieldSex = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 4)), 5);
            textFieldPhone = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 5)), 5);
            textFieldEmail = new JTextField(String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 6)), 5);
        } else {
//            textFieldSurname = new JTextField("", 5);
//            textFieldName = new JTextField("", 5);
//            textFieldBirthday = new JTextField("", 5);
//            textFieldSex = new JTextField("", 5);
//            textFieldPhone = new JTextField("", 5);
//            textFieldEmail = new JTextField("", 5);
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

    public static void go() {
        User app = new User();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        if (WindowUsers.action == "update") {
            String Surname = textFieldSurname.getText();
            String Name = textFieldName.getText();
            String Birthday = textFieldBirthday.getText();
            String Sex = textFieldSex.getText();
            String Phone = textFieldPhone.getText();
            String Email = textFieldEmail.getText();
            financialmanager.database.DbUsers.update(Surname, Name, Birthday, Sex, Phone, Email, currentId.toString(), WindowUsers.currentEmail.toString());
            modelUsers.fireTableDataChanged();
        }
        if (WindowUsers.action == "insert") {
            String Surname = textFieldSurname.getText();
            String Name = textFieldName.getText();
            String Birthday = textFieldBirthday.getText();
            String Sex = textFieldSex.getText();
            String Phone = textFieldPhone.getText();
            String Email = textFieldEmail.getText();
            financialmanager.database.DbUsers.insert(Surname, Name, Birthday, Sex, Phone, Email);
            modelUsers.fireTableDataChanged();
        }
    }

    private void onCancel() {
        // insert your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


