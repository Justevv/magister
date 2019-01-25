package financialmanager.database;

import financialmanager.data.Categories;
import financialmanager.gui.WindowCategories;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;
import static financialmanager.gui.Category.textFieldName;
import static financialmanager.gui.Category.textFieldParentId;
import static financialmanager.gui.WindowCategories.modelCategories;

public class DbCategories {
    public static Integer nId;
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

    public static void add() {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String Name = textFieldName.getText();
            String ParentId = textFieldParentId.getText();
            String insertSQLString = ("insert into t_dicCategories(sName, nParentId) values ('%1$s','%2$s')");
            String insertSQL = String.format(insertSQLString, Name, ParentId);
            stmt.executeUpdate(insertSQL);

            if (filternId < DbCategories.nId) {
                filternId = DbCategories.nId;
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
                modelCategories.fireTableDataChanged();
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

    public static void delete() {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicCategories where nId=%1$s");
            String insertSQL = String.format(insertSQLString, WindowCategories.value);
            stmt.executeUpdate(insertSQL);
            categories.remove(WindowCategories.selectedRows[WindowCategories.i - 1]);
            modelCategories.fireTableDataChanged();
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update() {
        updateGo();
    }

    public static void updateGo() {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String Name = textFieldName.getText();
            String ParentId = textFieldParentId.getText();
            if (String.valueOf(WindowCategories.model.getValueAt(WindowCategories.selIndex, 0)) != null) {
                currentCategoryId = String.valueOf(WindowCategories.model.getValueAt(WindowCategories.selIndex, 0));
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
                modelCategories.fireTableDataChanged();
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