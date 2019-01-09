package financialmanager.gui;

import financialmanager.database.DbExpenses;
import financialmanager.table.ExpensesTable;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static financialmanager.database.DbExpenses.expenses;

public class WindowExpenses extends JFrame {
    private JButton button = new JButton("Press");
    private JButton buttonAdd = new JButton("Добавить запись");
    private JButton buttonAddUser = new JButton("Пользователи");
    private JButton buttonAddCategory = new JButton("Категории");
    private JButton buttonAddPlace = new JButton("Места");
    private JTextField input = new JTextField("", 5);
    private JLabel label = new JLabel("Input:");
    private JLabel labelUser = new JLabel("Пользователь:" + DbExpenses.nUserSurname);
    private JLabel labelAccount = new JLabel("Номер счета:");
    public static JLabel labelBalance;
    private JRadioButton radio1 = new JRadioButton("Select this");
    private JRadioButton radio2 = new JRadioButton("Select that");
    private JRadioButton radio3 = new JRadioButton("Select no that");
    private JCheckBox check = new JCheckBox("Check", false);
    public static ExpensesTable tModel = new ExpensesTable(expenses);
    private static JTable jTabPeople = new JTable(tModel);
   // static JScrollPane jscrlp = new JScrollPane(jTabPeople);

    public WindowExpenses() {


        super("Финансовый менеджер");
        this.setBounds(100, 100, 650, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DbExpenses.main();

        labelUser = new JLabel("Пользователь: " + DbExpenses.nUserSurname);
        labelAccount = new JLabel("Номер счета: " + OpenWindow.userLogin);
        labelBalance = new JLabel("Баланс: " + DbExpenses.balance + " Рублей");
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
        tModel = new ExpensesTable(expenses);
        //    tModel.fireTableDataChanged();
        //На основе модели, создадим новую JTable
        jTabPeople = new JTable(tModel);
        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        JScrollPane jscrlp = new JScrollPane(jTabPeople);
        //    tModel.fireTableDataChanged();
        //Устаналиваем размеры прокручиваемой области
        jTabPeople.setPreferredScrollableViewportSize(new Dimension(450, 250));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

//        ButtonGroup group = new ButtonGroup();
//        group.add(radio1);
//        group.add(radio2);
//        group.add(radio3);

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

        buttonAddUser.addActionListener(new ActionListener() {
            //   private JInternalFrame dialog;

            public void actionPerformed(ActionEvent e) {
                WindowUsers.go();
            }
        });
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

        buttonAddPlace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                expenses.add(new financialmanager.data.Expenses(1, "20151010", "12300123", "2", "5", "7", 1));
                tModel.fireTableDataChanged();
            }
        });
        jfrm.add(buttonAddPlace);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddPlace, c);

        // Создание кнопки удаления строки таблицы
        // JButton remove = new JButton("Удалить");
//        buttonAddPlace.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Номер выделенной строки
//                int idx = jTabPeople.getSelectedRow();
//                // Удаление выделенной строки
//                jTabPeople.removeRow(idx);

        buttonAdd.addActionListener(new ActionListener() {
            //   private JInternalFrame dialog;

            public void actionPerformed(ActionEvent e) {
                Expenses.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAdd, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = GridBagConstraints.RELATIVE + 1;
        c.gridx = 1;
        c.gridy = 0;
        container.add(jscrlp, c);
        //container.add(jscrlp);
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
        WindowExpenses app = new WindowExpenses();
        app.setVisible(true);
    }

    public static void go() {
        WindowExpenses app = new WindowExpenses();
        app.setVisible(true);
            }
}
