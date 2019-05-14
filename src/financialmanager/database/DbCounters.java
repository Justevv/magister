package financialmanager.database;

import financialmanager.businesslogic.Counters;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbCounters {
    Integer nId = 0;
    public static ArrayList<financialmanager.data.Counters> counters = new ArrayList<>();
    String nUserSurname;
    String sqlSelect;
    Date dtDate;
    float nGasReadings;
    float nElectricityReadings;
    float nWaterReadings;
    float nGasPrice;
    float nElectricityPrice;
    float nWaterPrice;
    float nGasPaid;
    float nElectricityPaid;
    float nWaterPaid;

    public void view(String userId) {
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
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                dtDate = executeQuery.getDate("dtDate");
                nUserSurname = executeQuery.getString("sSurname");
                nGasReadings = executeQuery.getFloat("nGasReadings");
                nElectricityReadings = executeQuery.getFloat("nElectricityReadings");
                nWaterReadings = executeQuery.getFloat("nWaterReadings");
                nGasPrice = executeQuery.getFloat("nGasPrice");
                nElectricityPrice = executeQuery.getFloat("nElectricityPrice");
                nWaterPrice = executeQuery.getFloat("nWaterPrice");
                nGasPaid = executeQuery.getFloat("nGasPaid");
                nElectricityPaid = executeQuery.getFloat("nElectricityPaid");
                nWaterPaid = executeQuery.getFloat("nWaterPaid");
                Counters counters = new Counters();
                counters.start(nId, dtDate, nUserSurname, nGasReadings, nElectricityReadings, nWaterReadings, nGasPrice, nElectricityPrice, nWaterPrice, nGasPaid, nElectricityPaid, nWaterPaid);
            }
            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbCounters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void add(String userId, String dtDate, float nGasReadings, float nElectricityReadings, float nWaterReadings,
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
            // Закрываем соединение
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbCounters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Object idCounters, int selectedRows) {
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

    public void update(String userId, String dtDate, float nGasReadings, float nElectricityReadings, float nWaterReadings,
                       float nGasPrice, float nElectricityPrice, float nWaterPrice,
                       float nGasPaid, float nElectricityPaid, float nWaterPaid, String idCounters) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            if (String.valueOf(idCounters) != null) {
//                currentExpenseId = String.valueOf(idCounters);
            }
            String insertSQLString = ("update t_dicCounters set  dtDate='%1$s', nGasReadings='%3$s', nElectricityReadings='%4$s', nWaterReadings='%5$s'," +
                    "nGasPrice='%6$s', nElectricityPrice='%7$s', nWaterPrice='%8$s'," +
                    "nGasPaid='%9$s', nElectricityPaid='%10$s', nWaterPaid='%11$s' " +
                    "where nId=%12$s");
            String insertSQL = String.format(insertSQLString, dtDate, userId, nGasReadings, nElectricityReadings, nWaterReadings,
                    nGasPrice, nElectricityPrice, nWaterPrice,
                    nGasPaid, nElectricityPaid, nWaterPaid, idCounters);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbCounters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}