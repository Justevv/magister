package financialmanager.database;

import financialmanager.data.Categories;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbCategories {
    private int nId = 0;
    private String sName;
    private String currentCategoryId = "0";
    financialmanager.businesslogic.Categories categoriesBL = new financialmanager.businesslogic.Categories();

    public void select() {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicCategories"
            );
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                sName = executeQuery.getString("sName");
                Integer nParentId = executeQuery.getInt("nParentId");
                categoriesBL.categories.add(new Categories(nId, sName, nParentId));
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbCategories.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(String Name, String ParentId) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String insertSQLString = ("insert into t_dicCategories(sName, nParentId) values ('%1$s','%2$s')");
            String insertSQL = String.format(insertSQLString, Name, ParentId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void delete(String nId) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicCategories where nId=%1$s");
            String insertSQL = String.format(insertSQLString, nId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String Name, String ParentId, String currentId) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            if (currentId != null) {
                currentCategoryId = currentId;
            }
            String insertSQLString = ("update t_dicCategories set  sName='%1$s', nParentId='%2$s' where nId=%3$s");
            String insertSQL = String.format(insertSQLString, Name, ParentId, currentCategoryId);
            stmt.executeUpdate(insertSQL);
                       stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}