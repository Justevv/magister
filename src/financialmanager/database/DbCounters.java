package financialmanager.database;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbCounters {
    public static Integer nId = 0;
    public static Integer dSum;
    public static ArrayList<financialmanager.data.Counters> counters;
    public static Long balance;
    public static Long expense;
    public static Long profit;
    public static String nUserSurname;
    public static int filternId = 0000;
    static String currentExpenseId = "0";
    public static long profitCategory;
    public static long expenseCategory;
    public static long balanceCategory;
    static String sqlSelect;
    public static Date dtDate;
    public static Integer nGasReadings;
    public static Integer nElectricityReadings;
    public static Integer nWaterReadings;
    public static Integer nGasPrice;
    public static Integer nElectricityPrice;
    public static Integer nWaterPrice;
    public static Integer nGasPaid;
    public static Integer nElectricityPaid;
    public static Integer nWaterPaid;

    public static void view(String userId) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            sqlSelect = "SELECT c.[nId] " +
                    ",[dtDate]" +
                    ",u.[sSurname] as sSurname" +
                    ",[nGasReadings]" +
                    ",[nElectricityReadings]" +
                    ",[nWaterReadings]" +
                    ",[nGasPrice]" +
                    ",[nElectricityPrice]" +
                    ",[nWaterPrice]" +
                    ",[nGasPaid]" +
                    ",[nElectricityPaid]" +
                    ",[nWaterPaid]" +
                    "FROM [expenses].[dbo].[t_dicCounters] c join [expenses].[dbo].[t_dicUsers] u on c.nUserId=u.nId " +
                    "where c.nUserId='%1$s'" +
                    "and c.nId>'%2$s'";
            ResultSet executeQuery = stmt.executeQuery(String.format(sqlSelect, userId, 0));
            // Обход результатов выборки
            counters = new ArrayList<>();

            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                dtDate = executeQuery.getDate("dtDate");
                nUserSurname = executeQuery.getString("sSurname");
                nGasReadings = executeQuery.getInt("nGasReadings");
                nElectricityReadings = executeQuery.getInt("nElectricityReadings");
                nWaterReadings = executeQuery.getInt("nWaterReadings");
                nGasPrice = executeQuery.getInt("nGasPrice");
                nElectricityPrice = executeQuery.getInt("nElectricityPrice");
                nWaterPrice = executeQuery.getInt("nWaterPrice");
                nGasPaid = executeQuery.getInt("nGasPaid");
                nElectricityPaid = executeQuery.getInt("nElectricityPaid");
                nWaterPaid = executeQuery.getInt("nWaterPaid");
                financialmanager.businessLogic.Counters.start();
            }
            ResultSet executeQueryExpense = stmt.executeQuery("select sum(dSum) as Expense, count(dSum) as dCount from t_Expenses where nTransactionTypeId=2 and nUserId=" + userId);
            while (executeQueryExpense.next()) {
                expense = executeQueryExpense.getLong("Expense");
                Integer dCount = executeQueryExpense.getInt("dCount");
            }

            ResultSet executeQueryProfit = stmt.executeQuery("select sum(dSum) as Profit, count(dSum) as dCount from t_Expenses where  nTransactionTypeId=1 and nUserId=" + userId);
            while (executeQueryProfit.next()) {
                profit = executeQueryProfit.getLong("Profit");
                balance = profit - expense;
                Integer dCount = executeQueryProfit.getInt("dCount");
            }

            // Закрываем соединение
            executeQuery.close();
            executeQueryExpense.close();
            executeQueryProfit.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbCounters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void add(String userId, String dtDate, float nGasReadings, float nElectricityReadings, float nWaterReadings,
                           float nGasPrice, float nElectricityPrice, float nWaterPrice,
                           float nGasPaid, float nElectricityPaid, float nWaterPaid) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("insert into [t_dicCounters]" +
                    "(dtDate " +
                    ",nUserId " +
                    ",nGasReadings " +
                    ",nElectricityReadings " +
                    ",nWaterReadings " +
                    ",nGasPrice " +
                    ",nElectricityPrice " +
                    ",nWaterPrice " +
                    ",nGasPaid " +
                    ",nElectricityPaid " +
                    ",nWaterPaid) " +
                    "values " +
                    "('%1$s', %2$s, %3$s, %4$s, %5$s, %6$s, %7$s, %8$s, %9$s, %10$s, %11$s)");
            String insertSQL = String.format(insertSQLString, dtDate, userId, nGasReadings, nElectricityReadings, nWaterReadings,
                    nGasPrice, nElectricityPrice, nWaterPrice,
                    nGasPaid, nElectricityPaid, nWaterPaid);
            stmt.executeUpdate(insertSQL);

            if (nId != null && filternId < nId) {
                filternId = nId;
            }
            ResultSet executeQuery = stmt.executeQuery(String.format(sqlSelect, userId, filternId));
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                DbCounters.dtDate = executeQuery.getDate("dtDate");
                DbCounters.nUserSurname = executeQuery.getString("sSurname");
                DbCounters.nGasReadings = executeQuery.getInt("nGasReadings");
                DbCounters.nElectricityReadings = executeQuery.getInt("nElectricityReadings");
                DbCounters.nWaterReadings = executeQuery.getInt("nWaterReadings");
                DbCounters.nGasPrice = executeQuery.getInt("nGasPrice");
                DbCounters.nElectricityPrice = executeQuery.getInt("nElectricityPrice");
                DbCounters.nWaterPrice = executeQuery.getInt("nWaterPrice");
                DbCounters.nGasPaid = executeQuery.getInt("nGasPaid");
                DbCounters.nElectricityPaid = executeQuery.getInt("nElectricityPaid");
                DbCounters.nWaterPaid = executeQuery.getInt("nWaterPaid");
                financialmanager.businessLogic.Counters.start();
                filternId = nId;
            }
            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbCounters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void delete(Object idCounters, int selectedRows) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicCounters where nId=%1$s");
            String insertSQL = String.format(insertSQLString, idCounters);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbCounters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update(String userId, String dtDate, float nGasReadings, float nElectricityReadings, float nWaterReadings,
                              float nGasPrice, float nElectricityPrice, float nWaterPrice,
                              float nGasPaid, float nElectricityPaid, float nWaterPaid, String idCounters) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            if (String.valueOf(idCounters) != null) {
                currentExpenseId = String.valueOf(idCounters);
            }
            String insertSQLString = ("update t_dicCounters set  dtDate='%1$s', nGasReadings='%3$s', nElectricityReadings='%4$s', nWaterReadings='%5$s'," +
                    "nGasPrice='%6$s', nElectricityPrice='%7$s', nWaterPrice='%8$s'," +
                    "nGasPaid='%9$s', nElectricityPaid='%10$s', nWaterPaid='%11$s' " +
                    "where nId=%12$s");
            String insertSQL = String.format(insertSQLString, dtDate, userId, nGasReadings, nElectricityReadings, nWaterReadings,
                    nGasPrice, nElectricityPrice, nWaterPrice,
                    nGasPaid, nElectricityPaid, nWaterPaid, idCounters);
            stmt.executeUpdate(insertSQL);
            ResultSet executeQuery = stmt.executeQuery(String.format(sqlSelect, userId, 0));
            // Обход результатов выборки
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                DbCounters.dtDate = executeQuery.getDate("dtDate");
                DbCounters.nUserSurname = executeQuery.getString("sSurname");
                DbCounters.nGasReadings = executeQuery.getInt("nGasReadings");
                DbCounters.nElectricityReadings = executeQuery.getInt("nElectricityReadings");
                DbCounters.nWaterReadings = executeQuery.getInt("nWaterReadings");
                DbCounters.nGasPrice = executeQuery.getInt("nGasPrice");
                DbCounters.nElectricityPrice = executeQuery.getInt("nElectricityPrice");
                DbCounters.nWaterPrice = executeQuery.getInt("nWaterPrice");
                DbCounters.nGasPaid = executeQuery.getInt("nGasPaid");
                DbCounters.nElectricityPaid = executeQuery.getInt("nElectricityPaid");
                DbCounters.nWaterPaid = executeQuery.getInt("nWaterPaid");
                financialmanager.businessLogic.Counters.start();
                filternId = nId;
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbCounters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}