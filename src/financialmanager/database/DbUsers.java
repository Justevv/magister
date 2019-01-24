package financialmanager.database;

import financialmanager.data.Users;
import financialmanager.gui.AddUser;
import financialmanager.gui.UpdateUser;
import financialmanager.gui.WindowUsers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.gui.UpdateUser.*;
import static financialmanager.gui.AddUser.textFieldEmail;
import static financialmanager.gui.WindowUsers.modelUsers;

public class DbUsers {
    public static Integer nId;
    public static String sName;
    public static ArrayList<Users> users;
    public static String sEmail;
    public static ArrayList<String> sEmailList;
    private static String connectionString;
    public static int filternId = 0;
    static String currentUserId = "0";

    public static void connect() {
        // Формирование строки подключения
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
        connectionString = String.format(connectionUrl, instanceName, databaseName, userName, pass);
        //    Vector<Vector<Object>> retVector = new Vector<Vector<Object>>();
        //     Vector<Object> newRow = null;
    }

    public static void view() {
        connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicUsers"
            );
            // Обход результатов выборки
            users = new ArrayList<>();
            sEmailList = new ArrayList<>();
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                String sSurname = executeQuery.getString("sSurname");
                sName = executeQuery.getString("sName");
                String dtBirthday = executeQuery.getString("dtBirthday");
                String sSex = executeQuery.getString("sSex");
                String sPhone = executeQuery.getString("sPhone");
                sEmail = executeQuery.getString("sEmail");
                users.add(new Users(nId, sSurname, sName, dtBirthday, sSex, sPhone, sEmail));
                sEmailList.add(sEmail);
            }
            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void add() {
        boolean containsName = false;
        //(DbUsers.sEmailList,textFieldEmail.getText())
        //  boolean contains = Arrays.stream(DbUsers.sEmailMas).anyMatch(textFieldEmail.getText()::equals);
        //        System.out.println(Arrays.asList(DbUsers.sEmailList).contains(textFieldEmail.getText()));
        for (String sEmailList : DbUsers.sEmailList) {
            if (sEmailList.equals(textFieldEmail.getText())) {
                containsName = true;
                System.out.println("Такой пользователь уже есть");
            }
        }
        if (containsName == false) {
            connect();
            try {
                // Подключение к базе данных
                Connection con = DriverManager.getConnection(connectionString);
                // Отправка запроса на выборку и получение результатов
                Statement stmt = con.createStatement();
                String Surname = AddUser.textFieldSurname.getText();
                String Name = AddUser.textFieldName.getText();
                String Birthday = AddUser.textFieldBirthday.getText();
                String Sex = AddUser.textFieldSex.getText();
                String Phone = AddUser.textFieldPhone.getText();
                String Email = AddUser.textFieldEmail.getText();
                String insertSQLString = ("insert into t_dicUsers( sSurname ,sName, dtBirthday, sSex, sPhone, sEmail) values ('%1$s','%2$s','%3$s','%4$s','%5$s','%6$s')");
                String insertSQL = String.format(insertSQLString, Surname, Name, Birthday, Sex, Phone, Email);
                // System.out.println(insertSQL);
                stmt.executeUpdate(insertSQL);

                if (filternId < DbUsers.nId) {
                    filternId = DbUsers.nId;
                }
                ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                        "FROM t_dicUsers where nId>" + filternId
                );
                // Обход результатов выборки
                while (executeQuery.next()) {
                    int nId = executeQuery.getInt("nId");
                    String sSurname = executeQuery.getString("sSurname");
                    String sName = executeQuery.getString("sName");
                    String dtBirthday = executeQuery.getString("dtBirthday");
                    String sSex = executeQuery.getString("sSex");
                    String sPhone = executeQuery.getString("sPhone");
                    String sEmail = executeQuery.getString("sEmail");
                    users.add(new Users(nId, sSurname, sName, dtBirthday, sSex, sPhone, sEmail));
                    modelUsers.fireTableDataChanged();
                    filternId = nId;
                    DbUsers.sEmailList.add(sEmail);
                }
                stmt.close();
                con.close();
            } catch (
                    SQLException ex) {
                // Обработка исключений
                Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void delete() {
        connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicUsers where nId=%1$s");
            String insertSQL = String.format(insertSQLString, WindowUsers.result);
            stmt.executeUpdate(insertSQL);
            users.remove(WindowUsers.selectedRows[WindowUsers.i - 1]);
            modelUsers.fireTableDataChanged();
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update() {
        boolean containsName = false;
        for (String sEmailList : DbUsers.sEmailList) {
            if (sEmailList.equals(UpdateUser.textFieldEmail.getText())) {
                containsName = true;
            }
        }
        if ((WindowUsers.model.getValueAt(WindowUsers.selIndex, 6)).equals(UpdateUser.textFieldEmail.getText()) != true) {

            if (containsName == false) {
                updateGo();
            } else {
                System.out.println("Такой пользователь уже есть");
            }
        } else {
            updateGo();
        }
    }

    public static void updateGo() {
        connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String Surname = textFieldSurname.getText();
            String Name = textFieldName.getText();
            String Birthday = textFieldBirthday.getText();
            String Sex = textFieldSex.getText();
            String Phone = textFieldPhone.getText();
            String Email = UpdateUser.textFieldEmail.getText();
            DbUsers.sEmailList.add(Email);
            if (String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 0)) != null) {
                currentUserId = String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 0));
            }
            String insertSQLString = ("update t_dicUsers set  sSurname ='%1$s',sName='%2$s', dtBirthday='%3$s', sSex='%4$s', sPhone='%5$s', sEmail='%6$s'where nId=%7$s");
            String insertSQL = String.format(insertSQLString, Surname, Name, Birthday, Sex, Phone, Email, currentUserId);
            stmt.executeUpdate(insertSQL);
            users.removeAll(users);
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicUsers"
            );
            // Обход результатов выборки
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                String sSurname = executeQuery.getString("sSurname");
                String sName = executeQuery.getString("sName");
                String dtBirthday = executeQuery.getString("dtBirthday");
                String sSex = executeQuery.getString("sSex");
                String sPhone = executeQuery.getString("sPhone");
                String sEmail = executeQuery.getString("sEmail");
                users.add(new Users(nId, sSurname, sName, dtBirthday, sSex, sPhone, sEmail));
                modelUsers.fireTableDataChanged();
            }
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}