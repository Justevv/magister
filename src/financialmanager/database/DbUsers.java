package financialmanager.database;

import financialmanager.data.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbUsers {
    public static Integer nId;
    public static String sName;
    public static ArrayList<Users> users;
    public static String sEmail;
    public static ArrayList<String> sEmailList;
    public static int filternId = 0;
    static String currentUserId = "0";

    public static void view() {
        DbConnect.connect();
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

    public static void add(String Surname, String Name, String Birthday, String Sex, String Phone, String Email) {
        boolean containsName = false;
        for (String sEmailList : sEmailList) {
            if (sEmailList.equals(Email)) {
                containsName = true;
                System.out.println("Такой пользователь уже есть");
            }
        }
        if (containsName == false) {
            DbConnect.connect();
            try {
                // Подключение к базе данных
                Connection con = DriverManager.getConnection(connectionString);
                // Отправка запроса на выборку и получение результатов
                Statement stmt = con.createStatement();
                String insertSQLString = ("insert into t_dicUsers( sSurname ,sName, dtBirthday, sSex, sPhone, sEmail) values ('%1$s','%2$s','%3$s','%4$s','%5$s','%6$s')");
                String insertSQL = String.format(insertSQLString, Surname, Name, Birthday, Sex, Phone, Email);
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
                    filternId = nId;
                    sEmailList.add(sEmail);
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

    public static void delete(String nId) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicUsers where nId=%1$s");
            String insertSQL = String.format(insertSQLString, nId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update(String Surname, String Name, String Birthday, String Sex, String Phone, String Email, String Id, String currentEmail) {
        boolean containsName = false;
        for (String sEmailList : sEmailList) {
            if (sEmailList.equals(Email)) {
                containsName = true;
            }
        }
        if (currentEmail.equals(Email) != true) {

            if (containsName == false) {
                updateGo(Surname, Name, Birthday, Sex, Phone, Email, Id);
            } else {
                System.out.println("Такой пользователь уже есть");
            }
        } else {
            updateGo(Surname, Name, Birthday, Sex, Phone, Email, Id);
        }
    }

    public static void updateGo(String Surname, String Name, String Birthday, String Sex, String Phone, String Email, String Id) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            sEmailList.add(Email);
            if (Id != null) {
                currentUserId = Id;
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