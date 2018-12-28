package financialmanager.gui;

import financialmanager.data.Expenses;
import financialmanager.database.DbExpenses;
import financialmanager.table.ExpensesTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbExpenses.expenses;
import static financialmanager.gui.WindowExpenses.tModel;

public class AddExpenses extends JFrame {
    private JPanel contentPane = new JPanel();
    private JButton button = new JButton("Press");
    private JButton buttonAdd = new JButton("Добавить запись");
    private JButton buttonAddUser = new JButton("Пользователи");
    private JButton buttonAddCategory = new JButton("Категории");
    private JButton buttonAddPlace = new JButton("Места");
    private JTextField input = new JTextField("", 5);
    private JLabel label = new JLabel("Input:");
    private JLabel labelUser = new JLabel("Пользователь:" + DbExpenses.nUserSurname);
    private JLabel labelAccount = new JLabel("Номер счета:");
    private JLabel labelDate = new JLabel("Дата:");
    private JLabel labelCategory = new JLabel("Категория:");
    private JLabel labelPlace = new JLabel("Место:");
    private JLabel labelPaymentType = new JLabel("Тип оплаты:");
    private JLabel labelSum = new JLabel("Сумма:");
    private JTextField textFieldDate = new JTextField("20181010", 5);
    private JComboBox comboBoxCategory;
    public static JComboBox comboBoxPlace;
    private JComboBox comboBoxPaymentType;
    private JTextField textFieldSum = new JTextField("100", 5);
    public static JLabel labelBalance;
    private JRadioButton radio1 = new JRadioButton("Select this");
    private JRadioButton radio2 = new JRadioButton("Select that");
    private JRadioButton radio3 = new JRadioButton("Select no that");
    private JCheckBox check = new JCheckBox("Check", false);
    public static JComboBox comboBoxUsers;
    final static boolean shouldWeightX = true;
    String item;
    Font font = new Font("Verdana", Font.PLAIN, 18);
    //    int nullcolumn;
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");
    public static int filternId = 50000;

    public AddExpenses() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        labelUser = new JLabel("Пользователь: " + DbExpenses.nUserSurname);
        labelAccount = new JLabel("Номер счета: " + OpenWindow.userLogin);
        labelBalance = new JLabel("Баланс: " + DbExpenses.balance + " Рублей");
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

//        final JLabel label5 = new JLabel("test ");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = 0;
//        label.setAlignmentX(LEFT_ALIGNMENT);
//        label5.setFont(font);
        // container.add(label5,c);

//        ActionListener actionListener5 = new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JComboBox comboBoxPaymentType = (JComboBox)e.getSource();
//                item = (String)comboBoxPaymentType.getSelectedItem();
//                label5.setText(item);
//            }
//        };

//        JComboBox comboBoxUsers = new JComboBox(DbExpenses.items);
//        comboBoxUsers.setFont(font);
//        comboBoxUsers.addActionListener(actionListener5);
//        comboBoxUsers.setEditable(true);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelDate, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelCategory, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelPaymentType, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelPlace, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(labelSum, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldDate, c);

        comboBoxCategory = new JComboBox(DbExpenses.items);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxCategory, c);

        comboBoxPaymentType = new JComboBox(DbExpenses.items);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxPaymentType, c);

       // comboBoxPlace = new JComboBox();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(comboBoxPlace, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldSum, c);

        buttonOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // JComboBox comboBoxPaymentType = (JComboBox)e.getSource();
                item = (String) comboBoxPaymentType.getSelectedItem();
                onOK();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        container.add(buttonOK, c);

        button.addActionListener(new ButtonEventListener());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 5;
        container.add(buttonCancel, c);

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
        AddExpenses app = new AddExpenses();
        app.setVisible(true);
    }

    public static void go() {
        AddExpenses app = new AddExpenses();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        // add your code here
        // Формирование строки подключения
        System.out.println(item);
        String computername = null;
        try {
            computername = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String instanceName = computername + "\\SQLEXPRESS";
        String databaseName = "expenses";
        String userName = "supertest";
        String pass = "supertest";
        String connectionUrl = "jdbc:sqlserver://%1$s;databaseName=%2$s;user=%3$s;password=%4$s;";
        String connectionString = String.format(connectionUrl, instanceName, databaseName, userName, pass);
        //    Vector<Vector<Object>> retVector = new Vector<Vector<Object>>();
        //     Vector<Object> newRow = null;
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String dtDate = new String(textFieldDate.getText());
            Integer dSum = new Integer(textFieldSum.getText());
            //  String nUserId = item;
            String nCategoryId = item;
            String nPlaceId = item;
            String nPaymentTypeId = item;
            String insertSQLString = ("insert into t_Expenses( dtDate ,dSum,nUserId, nCategoryId ,nPlaceId,nPaymentTypeId) values ('%1$s',%2$s,%3$s,%4$s,%5$s,%6$s)");
            String insertSQL = String.format(insertSQLString, dtDate, dSum, OpenWindow.userLogin, nCategoryId, nPlaceId, nPaymentTypeId);
            // System.out.println(insertSQL);
            stmt.executeUpdate(insertSQL);
            // Закрываем соединение
            //executeQuery.close();

            // Подключение к базе данных
            //   Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            //   Statement stmt = con.createStatement();

            //  System.out.println("SUP " + DbExpenses.nId);
            if (DbExpenses.nId != null && filternId < DbExpenses.nId) {
                filternId = DbExpenses.nId;
            }
//               System.out.println("UP " + filternId);
//               System.out.println("DB " + DbExpenses.nId);
            ResultSet executeQuery = stmt.executeQuery("SELECT e.nId as nId, dtDate, sSurname, c.sName as CategoriesName,p.sName as nPlaceName, pt.sName as nPaymentTypeName, dSum " +
                    "FROM t_Expenses e join t_dicUsers u on e.nUserId=u.nId " +
                    "join t_dicCategories c on e.nCategoryId=c.nId " +
                    "join t_dicPlaces p on e.nPlaceId=p.nId " +
                    "join t_dicPaymentTypes pt on e.nPaymentTypeId=pt.nId " +
                    "where e.nUserId=" + OpenWindow.userLogin
                    + "and e.nId>" + filternId
            );
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                dtDate = executeQuery.getString("dtDate");
                String nUserSurname = executeQuery.getString("sSurname");
                String nCategoryName = executeQuery.getString("CategoriesName");
                String nPlaceName = executeQuery.getString("nPlaceName");
                String nPaymentTypeName = executeQuery.getString("nPaymentTypeName");
                dSum = executeQuery.getInt("dSum");
                expenses.add(new Expenses(nId, dtDate, nUserSurname, nCategoryName, nPlaceName, nPaymentTypeName, dSum));
                tModel.fireTableDataChanged();
                filternId = nId;
//                     System.out.println("Down " + filternId);
            }
            ResultSet executeQuery2 = stmt.executeQuery("select sum(dSum) as dSum, count(dSum) as dCount from t_Expenses where nUserId=" + OpenWindow.userLogin);
            while (executeQuery2.next()) {
                long balance = executeQuery2.getLong("dSum");
                Integer dCount = executeQuery2.getInt("dCount");
            }

            // Закрываем соединение
            executeQuery.close();

            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
        // dispose();
        //  setVisible(false);
        DbExpenses.balance = DbExpenses.balance + new Integer(textFieldSum.getText());
        WindowExpenses.labelBalance.setText("Баланс: " + DbExpenses.balance + " Рублей");
    }

    private void onCancel() {
        // add your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}
