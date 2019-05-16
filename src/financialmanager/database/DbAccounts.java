package financialmanager.database;

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
            Accounts accounts = new Accounts();
            accounts.removeList();
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicAccounts");
            while (executeQuery.next()) {
                id = executeQuery.getInt("nId");
                name = executeQuery.getString("sName");
                accounts.accounts.add(new Accounts(id, name));
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbAccounts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(String name) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String insertSQLString = ("insert into t_dicAccounts(sName) values ('%1$s')");
            String insertSQL = String.format(insertSQLString, name);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(String id) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicAccounts where nId=%1$s");
            String insertSQL = String.format(insertSQLString, id);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String name, String currentId) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String insertSQLString = ("update t_dicAccounts set sName='%1$s' where nId=%2$s");
            String insertSQL = String.format(insertSQLString, name, currentId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}