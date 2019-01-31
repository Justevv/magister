package financialmanager.database;

import financialmanager.data.Categories;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbCategories {
    public static Integer nId = 0;
    public static String sName;
    public static ArrayList<Categories> categories;
    public static int filternId = 0;
    static String currentCategoryId = "0";

    public static void view() {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicCategories"
            );
            // Обход результатов выборки
            categories = new ArrayList<>();
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                sName = executeQuery.getString("sName");
                Integer nParentId = executeQuery.getInt("nParentId");
                categories.add(new Categories(nId, sName, nParentId));
            }
            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbCategories.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void add(String Name, String ParentId) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("insert into t_dicCategories(sName, nParentId) values ('%1$s','%2$s')");
            String insertSQL = String.format(insertSQLString, Name, ParentId);
            stmt.executeUpdate(insertSQL);

            if (filternId < nId) {
                filternId = nId;
            }
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicCategories where nId>" + filternId
            );
            // Обход результатов выборки
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                ;
                String sName = executeQuery.getString("sName");
                Integer nParentId = executeQuery.getInt("nParentId");
                categories.add(new Categories(nId, sName, nParentId));
                filternId = nId;
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void delete(String nId) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicCategories where nId=%1$s");
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

    public static void update(String Name, String ParentId, String currentId) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            if (currentId != null) {
                currentCategoryId = currentId;
            }
            String insertSQLString = ("update t_dicCategories set  sName='%1$s', nParentId='%2$s' where nId=%3$s");
            String insertSQL = String.format(insertSQLString, Name, ParentId, currentCategoryId);
            stmt.executeUpdate(insertSQL);
            categories.removeAll(categories);
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicCategories"
            );
            // Обход результатов выборки
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                String sName = executeQuery.getString("sName");
                Integer nParentId = executeQuery.getInt("nParentId");
                categories.add(new Categories(nId, sName, nParentId));
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}