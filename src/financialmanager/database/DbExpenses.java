package financialmanager.database;

import financialmanager.gui.Expense;
import financialmanager.gui.OpenWindow;
import financialmanager.gui.WindowExpenses;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;
import static financialmanager.gui.Expense.textFieldSum;
import static financialmanager.gui.WindowExpenses.modelExpenses;

public class DbExpenses {
    public static Integer nId;
    public static Integer dSum;
    public static ArrayList<financialmanager.data.Expenses> expenses;
    public static Long balance;
    public static String nUserSurname;
    public static int filternId = 0000;
    static String Place;
    static String PaymentType;
    static String Category;
    static String currentExpenseId = "0";

    public static void view() {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT e.nId as nId, dtDate, sSurname, c.sName as CategoriesName,p.sName as nPlaceName, pt.sName as nPaymentTypeName, dSum " +
                    "FROM t_Expenses e join t_dicUsers u on e.nUserId=u.nId " +
                    "join t_dicCategories c on e.nCategoryId=c.nId " +
                    "join t_dicPlaces p on e.nPlaceId=p.nId " +
                    "join t_dicPaymentTypes pt on e.nPaymentTypeId=pt.nId " +
                    "where e.nUserId=" + OpenWindow.userLogin + "and e.nId>" + financialmanager.database.Expenses.filternId
            );
            // Обход результатов выборки
            expenses = new ArrayList<>();

            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                String dtDate = executeQuery.getString("dtDate");
                nUserSurname = executeQuery.getString("sSurname");
                String nCategoryName = executeQuery.getString("CategoriesName");
                String nPlaceName = executeQuery.getString("nPlaceName");
                String nPaymentTypeName = executeQuery.getString("nPaymentTypeName");
                dSum = executeQuery.getInt("dSum");
                expenses.add(new financialmanager.data.Expenses(nId, dtDate, nUserSurname, nCategoryName, nPlaceName, nPaymentTypeName, dSum));
                WindowExpenses.modelExpenses.fireTableDataChanged();
            }
            ResultSet executeQuerySum = stmt.executeQuery("select sum(dSum) as dSum, count(dSum) as dCount from t_Expenses where nUserId=" + OpenWindow.userLogin
            );
            while (executeQuerySum.next()) {
                balance = executeQuerySum.getLong("dSum");
                Integer dCount = executeQuerySum.getInt("dCount");
            }

            // Закрываем соединение
            executeQuery.close();
            executeQuerySum.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void comboBoxRead() {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();

            Expense.comboBoxPlace = new JComboBox();
            Expense.comboBoxPaymentType = new JComboBox();
            Expense.comboBoxCategory = new JComboBox();

            ResultSet executeQueryNamePlaces = stmt.executeQuery("select * from t_dicPlaces");
            while (executeQueryNamePlaces.next()) {
                Expense.comboBoxPlace.addItem(executeQueryNamePlaces.getString("sName"));
//                Expense.comboBoxPlace.getItemAt(3);
            }

            ResultSet executeQueryNamePaymentTypes = stmt.executeQuery("select * from t_dicPaymentTypes");
            while (executeQueryNamePaymentTypes.next()) {
                Expense.comboBoxPaymentType.addItem(executeQueryNamePaymentTypes.getString("sName"));
            }

            ResultSet executeQueryNameCategories = stmt.executeQuery("select * from t_dicCategories");
            while (executeQueryNameCategories.next()) {
                Expense.comboBoxCategory.addItem(executeQueryNameCategories.getString("sName"));
            }

            // Закрываем соединение
            executeQueryNamePlaces.close();
            executeQueryNamePaymentTypes.close();
            executeQueryNameCategories.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void add() {
        Place = (String) Expense.comboBoxPlace.getSelectedItem();
        PaymentType = (String) Expense.comboBoxPaymentType.getSelectedItem();
        Category = (String) Expense.comboBoxCategory.getSelectedItem();
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String dtDate = Expense.textFieldDate.getText();
            Integer dSum = new Integer(textFieldSum.getText());
            String insertSQLString = ("insert into t_Expenses( dtDate ,dSum,nUserId, nCategoryId ,nPlaceId,nPaymentTypeId) " +
                    "values " +
                    "('%1$s',%2$s,%3$s," +
                    "(select nId from [dbo].[t_dicCategories] where sName='%4$s')" +
                    ",(select nId from [dbo].[t_dicPlaces] where sName='%5$s')" +
                    ",(select nId from [dbo].[t_dicPaymentTypes] where sName='%6$s'))");
            String insertSQL = String.format(insertSQLString, dtDate, dSum, OpenWindow.userLogin, Category, Place, PaymentType);
            stmt.executeUpdate(insertSQL);
            balance = balance + new Integer(textFieldSum.getText());
            // Закрываем соединение
            //executeQuery.close();

            if (DbExpenses.nId != null && filternId < DbExpenses.nId) {
                filternId = DbExpenses.nId;
            }

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
                expenses.add(new financialmanager.data.Expenses(nId, dtDate, nUserSurname, nCategoryName, nPlaceName, nPaymentTypeName, dSum));
                modelExpenses.fireTableDataChanged();
                filternId = nId;
//                     System.out.println("Down " + filternId);
            }

            // Закрываем соединение
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
            String insertSQLString = ("delete from t_Expenses where nId=%1$s");
            String insertSQL = String.format(insertSQLString, WindowExpenses.value);
            stmt.executeUpdate(insertSQL);
            expenses.remove(WindowExpenses.selectedRows[WindowExpenses.i - 1]);
            modelExpenses.fireTableDataChanged();
            balance = balance - (Integer) WindowExpenses.Sum;
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update() {
        Place = (String) Expense.comboBoxPlace.getSelectedItem();
        PaymentType = (String) Expense.comboBoxPaymentType.getSelectedItem();
        Category = (String) Expense.comboBoxCategory.getSelectedItem();
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String dtDate = Expense.textFieldDate.getText();
            Integer dSum = new Integer(textFieldSum.getText());
            if (String.valueOf(WindowExpenses.model.getValueAt(WindowExpenses.selIndex, 0)) != null) {
                currentExpenseId = String.valueOf(WindowExpenses.model.getValueAt(WindowExpenses.selIndex, 0));
            }
            String insertSQLString = ("update t_Expenses set  dtDate='%1$s', dSum='%2$s', nUserId='%3$s'" +
                    ", nCategoryId=(select nId from [dbo].[t_dicCategories] where sName='%4$s')" +
                    ", nPlaceId=(select nId from [dbo].[t_dicPlaces] where sName='%5$s')" +
                    ", nPaymentTypeId=(select nId from [dbo].[t_dicPaymentTypes] where sName='%6$s')" +
                    " where nId=%7$s");
            String insertSQL = String.format(insertSQLString, dtDate, dSum, OpenWindow.userLogin, Category, Place, PaymentType, currentExpenseId);
            stmt.executeUpdate(insertSQL);
            expenses.removeAll(expenses);
            balance = balance - (Integer) WindowExpenses.Sum + dSum;
            WindowExpenses.Sum = dSum;
            ResultSet executeQuery = stmt.executeQuery("SELECT e.nId as nId, dtDate, sSurname, c.sName as CategoriesName,p.sName as nPlaceName, pt.sName as nPaymentTypeName, dSum " +
                    "FROM t_Expenses e join t_dicUsers u on e.nUserId=u.nId " +
                    "join t_dicCategories c on e.nCategoryId=c.nId " +
                    "join t_dicPlaces p on e.nPlaceId=p.nId " +
                    "join t_dicPaymentTypes pt on e.nPaymentTypeId=pt.nId " +
                    "where e.nUserId=" + OpenWindow.userLogin
            );
            // Обход результатов выборки
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                dtDate = executeQuery.getString("dtDate");
                String nUserSurname = executeQuery.getString("sSurname");
                String nCategoryName = executeQuery.getString("CategoriesName");
                String nPlaceName = executeQuery.getString("nPlaceName");
                String nPaymentTypeName = executeQuery.getString("nPaymentTypeName");
                dSum = executeQuery.getInt("dSum");
                expenses.add(new financialmanager.data.Expenses(nId, dtDate, nUserSurname, nCategoryName, nPlaceName, nPaymentTypeName, dSum));
                modelExpenses.fireTableDataChanged();
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