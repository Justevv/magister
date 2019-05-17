package financialmanager.database;

import financialmanager.data.Accounts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbAccounts {
    private DbConnect dbConnect = new DbConnect();
    private static ArrayList<Accounts> accounts = new ArrayList<>();


    public List<Accounts> select() {
//        List<Accounts> accounts = new ArrayList<>();
        accounts.removeAll(accounts);
        try {
            Statement stmt = dbConnect.connect();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicAccounts");
            while (executeQuery.next()) {
                int id = executeQuery.getInt("nId");
                String name = executeQuery.getString("sName");
                accounts.add(new Accounts(id, name));
            }
            executeQuery.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbAccounts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    public void insert(String name) {
        try {
            Statement stmt = dbConnect.connect();
            String insertSQLString = ("insert into t_dicAccounts(sName) values ('%1$s')");
            String insertSQL = String.format(insertSQLString, name);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(String id) {
        try {
            Statement stmt = dbConnect.connect();
            String insertSQLString = ("delete from t_dicAccounts where nId=%1$s");
            String insertSQL = String.format(insertSQLString, id);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String name, String currentId) {
        try {
            Statement stmt = dbConnect.connect();
            String insertSQLString = ("update t_dicAccounts set sName='%1$s' where nId=%2$s");
            String insertSQL = String.format(insertSQLString, name, currentId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}