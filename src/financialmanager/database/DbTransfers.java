package financialmanager.database;

import financialmanager.data.Transfers;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbTransfers {
    public static Integer nId;
    public static String nAccountSender;
    public static ArrayList<Transfers> Transfers;
    public static int filternId = 0;
    static String currentTransferId = "0";
    static String sqlSelectTransfers;

    public static void view(String nUserId) {
        sqlSelectTransfers ="SELECT t.nId" +
                ", a.sName as AccountSender" +
                ", a1.sName as AccountRecipient" +
                ", t.dSum " +
                "FROM t_dicTransfers t join t_dicAccounts a on t.nAccountSenderId=a.nId " +
                "join t_dicAccounts a1 on  t.nAccountRecipientId=a1.nId " +
                "where nUserId='%1$s'" +
                "and t.nId>'%2$s'";
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery(String.format(sqlSelectTransfers,nUserId, filternId));
            // Обход результатов выборки
            Transfers = new ArrayList<>();
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                nAccountSender = executeQuery.getString("AccountSender");
                String nAccountRecipient = executeQuery.getString("AccountRecipient");
                Integer dSum = executeQuery.getInt("dSum");
                Transfers.add(new Transfers(nId, nAccountSender, nAccountRecipient, dSum));
            }
            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbTransfers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void comboBoxTransfer(JComboBox comboBoxAccountSender, JComboBox comboBoxAccountRecipient) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();

            ResultSet executeQueryNameAccounts = stmt.executeQuery("select * from t_dicAccounts");
            while (executeQueryNameAccounts.next()) {
                comboBoxAccountSender.addItem(executeQueryNameAccounts.getString("sName"));
                comboBoxAccountRecipient.addItem(executeQueryNameAccounts.getString("sName"));
            }
            // Закрываем соединение
            executeQueryNameAccounts.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void add(String nAccountSenderId, String nAccountRecipientId, Integer dSum, String UserId) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("insert into t_dicTransfers(nUserId, nAccountSenderId, nAccountRecipientId, dSum)" +
                    " values" +
                    "('%1$s',"+
                    "(select nId from t_dicAccounts where sName='%2$s')," +
                    "(select nId from t_dicAccounts where sName='%3$s')," +
                    " '%4$s')");
            String insertSQL = String.format(insertSQLString, UserId, nAccountSenderId, nAccountRecipientId, dSum);
            stmt.executeUpdate(insertSQL);

            if (filternId < DbTransfers.nId) {
                filternId = DbTransfers.nId;
            }
            ResultSet executeQuery = stmt.executeQuery("SELECT * FROM t_dicTransfers where nId>" + filternId);
            // Обход результатов выборки
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                nAccountSender = executeQuery.getString("nAccountSenderId");
                String nAccountRecipient = executeQuery.getString("nAccountRecipientId");
                dSum = executeQuery.getInt("dSum");
                Transfers.add(new Transfers(nId, nAccountSender, nAccountRecipient, dSum));
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

    public static void delete(Object value, int selectedRows) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicTransfers where nId=%1$s");
            String insertSQL = String.format(insertSQLString, value);
            stmt.executeUpdate(insertSQL);
            Transfers.remove(selectedRows);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update(String nAccountSenderId, String nAccountRecipientId, Integer dSum, String value) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            if (value != null) {
                currentTransferId = value;
            }
            String insertSQLString = ("update t_dicTransfers set  nAccountSender='%1$s', nAccountRecipientId='%2$s', dSum=%3$s where nId=%4$s");
            String insertSQL = String.format(insertSQLString, nAccountSenderId, nAccountRecipientId, dSum, currentTransferId);
            stmt.executeUpdate(insertSQL);
            Transfers.removeAll(Transfers);
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicTransfers"
            );
            // Обход результатов выборки
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                nAccountSenderId = executeQuery.getString("nAccountSender");
                nAccountRecipientId = executeQuery.getString("nAccountRecipientId");
                dSum = executeQuery.getInt("dSum");
                Transfers.add(new Transfers(nId, nAccountSenderId, nAccountRecipientId, dSum));
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