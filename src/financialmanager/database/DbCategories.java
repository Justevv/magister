package financialmanager.database;

import financialmanager.data.Categories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbCategories {
    private String currentCategoryId = "0";
    private DbConnect dbConnect = new DbConnect();

    public List<Categories> select() {
        List<Categories> categories = new ArrayList<>();
        try {
            Statement stmt = dbConnect.connect();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicCategories"
            );
            while (executeQuery.next()) {
                int id = executeQuery.getInt("nId");
                String name = executeQuery.getString("sName");
                int parentId = executeQuery.getInt("nParentId");
                categories.add(new Categories(id, name, parentId));
            }
            executeQuery.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbCategories.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public void insert(String Name, String ParentId) {
        try {
            Statement stmt = dbConnect.connect();
            String insertSQLString = ("insert into t_dicCategories(sName, nParentId) values ('%1$s','%2$s')");
            String insertSQL = String.format(insertSQLString, Name, ParentId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(String nId) {
        try {
            Statement stmt = dbConnect.connect();
            String insertSQLString = ("delete from t_dicCategories where nId=%1$s");
            String insertSQL = String.format(insertSQLString, nId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String Name, String ParentId, String currentId) {
        try {
            Statement stmt = dbConnect.connect();
            if (currentId != null) {
                currentCategoryId = currentId;
            }
            String insertSQLString = ("update t_dicCategories set  sName='%1$s', nParentId='%2$s' where nId=%3$s");
            String insertSQL = String.format(insertSQLString, Name, ParentId, currentCategoryId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}