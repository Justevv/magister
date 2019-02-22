package financialmanager.database;

//import financialmanager.data.Accounts;

import financialmanager.data.Accounts;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbAccounts {
    private int id;
    private String name;
//    public static ArrayList<Accounts> accounts = new ArrayList<>();

    public void select() {
        DbConnect.connect();
        try {
            Accounts accounts = new Accounts(id, name);
            accounts.removeList();
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicAccounts");
            // Обход результатов выборки
            while (executeQuery.next()) {
                id = executeQuery.getInt("nId");
                name = executeQuery.getString("sName");
//                accounts.add(new Accounts(id, name));
                accounts.insertList(id, name);
            }
            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbAccounts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(String name) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("insert into t_dicAccounts(sName) values ('%1$s')");
            String insertSQL = String.format(insertSQLString, name);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(String id) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicAccounts where nId=%1$s");
            String insertSQL = String.format(insertSQLString, id);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String name, String currentId) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("update t_dicAccounts set sName='%1$s' where nId=%2$s");
            String insertSQL = String.format(insertSQLString, name, currentId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}