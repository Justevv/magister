package financialmanager.gui;

import financialmanager.database.DbExpenses;
import financialmanager.database.DbUsers;
import financialmanager.table.UsersTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowUsers extends JFrame implements ActionListener {
    private JButton button = new JButton("Press");
    private JButton buttonAdd = new JButton("Добавить запись");
    private JButton buttonAddUser = new JButton("Пользователи");
    private JButton buttonAddCategory = new JButton("Категории");
    private JButton buttonAddPlace = new JButton("Места");
    private JTextField input = new JTextField("", 5);
    private JLabel label = new JLabel("Input:");
    private JLabel labelUser = new JLabel("Пользователь:"+ DbExpenses.sName);
    private JLabel labelAccount = new JLabel("Номер счета:");
    private JLabel labelBalance = new JLabel("Баланс:");
    private JRadioButton radio1 = new JRadioButton("Select this");
    private JRadioButton radio2 = new JRadioButton("Select that");
    private JRadioButton radio3 = new JRadioButton("Select no that");
    private JCheckBox check = new JCheckBox("Check", false);
    private UsersTable tModel;
    private JTable jTabPeople;
    private JScrollPane jscrlp = new JScrollPane(jTabPeople);
    final static boolean shouldWeightX = true;
    private JTextField sSurname = new JTextField("", 5);
    private JTextField sName = new JTextField("", 5);
    private JTextField dtDirthday = new JTextField("", 5);
    private JTextField sSex = new JTextField("", 5);
    private JTextField sPhone = new JTextField("", 5);
    private JTextField sEmail = new JTextField("", 5);
//    int nullcolumn;

    public WindowUsers() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 750, 500);
      //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DbUsers.main();
    //    DbExpenses.main();

        labelUser = new JLabel("Пользователь: "+ DbExpenses.nUserSurname);
        labelAccount = new JLabel("Номер счета: "+ OpenWindow.userLogin);
        labelBalance = new JLabel("Баланс: "+ DbExpenses.balance+" Рублей");
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = 0;
//        container.add(label, c);
    //    container.add(label);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelUser, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelAccount, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelBalance, c);

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 1;
//        c.gridy = 0;
//        container.add(input, c);
//      //  container.add(input);

        JFrame jfrm = new JFrame("JTableExample");
        tModel = new UsersTable(DbUsers.users);
        //На основе модели, создадим новую JTable
        jTabPeople = new JTable(tModel);
        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        JScrollPane jscrlp = new JScrollPane(jTabPeople);
        //Устаналиваем размеры прокручиваемой области
        jTabPeople.setPreferredScrollableViewportSize(new Dimension(350, 200));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        group.add(radio3);

//        if (shouldWeightX) {
//            c.weightx = 0.5;
//        }
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 1;
//        container.add(radio1, c);
//        //container.add(radio1);

//        radio1.setSelected(true);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 1;
//        c.gridy = 1;
//        container.add(radio2, c);
//       // container.add(radio2);

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 2;
//        c.gridy = 1;
//        container.add(radio3, c);
//      //  container.add(radio3);

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = 2;
//        container.add(check, c);
//      //  container.add(check);

        button.addActionListener(new ButtonEventListener());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        container.add(button, c);
      //  container.add(button);

        buttonAddUser.addActionListener(new ButtonEventListener());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddUser, c);

        buttonAddCategory.addActionListener(new ButtonEventListener());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddCategory, c);

        buttonAddPlace.addActionListener(new ButtonEventListener());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddPlace, c);

        buttonAdd.addActionListener(new ButtonEventListener());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAdd, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth=1 ;
        c.gridheight=GridBagConstraints.RELATIVE+1 ;
        c.gridx = 1;
        c.gridy = 0;
        container.add(jscrlp, c);
        //container.add(jscrlp);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(sSurname, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = "";
            message += "Button was pressed\n";
            message += "Text is " + input.getText() + "\n";
            message += (radio1.isSelected() ? "Radio #1" : "Radio #2")
                    + " is selected\n";
            message += "CheckBox is " + ((check.isSelected())
                    ? "checked" : "unchecked");
            JOptionPane.showMessageDialog(null,
                    message,
                    "Output",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        WindowUsers app = new WindowUsers();
        app.setVisible(true);
    }
    public static void go(){
        WindowUsers app = new WindowUsers();
        app.setVisible(true);
    };
}
