package financialmanager.database;

import financialmanager.data.Transfers;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbTransfers {
    public static Integer nId = 0;
    public static String nAccountSender;
    public static ArrayList<Transfers> Transfers;
    public static int filterId = 0;
    static String currentTransferId = "0";
    static String sqlSelectTransfers;
    private static long expenseTransiction;
    private static long profitTransiction;
    public static long balanceTransiction;

    public static void select(String UserId) {
        sqlSelectTransfers = "SELECT t.nId" +
                ", a.sName as AccountSender" +
                ", a1.sName as AccountRecipient" +
                ", t.dSum " +
                "FROM t_dicTransfers t join t_dicAccounts a on t.nAccountSenderId=a.nId " +
                "join t_dicAccounts a1 on  t.nAccountRecipientId=a1.nId " +
                "where nUserId=%1$s" +
                "and t.nId>%2$s";
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery(String.format(sqlSelectTransfers, UserId, 0));
            Transfers = new ArrayList<>();
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                nAccountSender = executeQuery.getString("AccountSender");
                String nAccountRecipient = executeQuery.getString("AccountRecipient");
                Integer dSum = executeQuery.getInt("dSum");
                Transfers.add(new Transfers(nId, nAccountSender, nAccountRecipient, dSum));
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbTransfers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void comboBoxTransfer(JComboBox comboBoxAccountSender, JComboBox comboBoxAccountRecipient) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            ResultSet executeQueryNameAccounts = stmt.executeQuery("select * from t_dicAccounts");
            while (executeQueryNameAccounts.next()) {
                comboBoxAccountSender.addItem(executeQueryNameAccounts.getString("sName"));
                comboBoxAccountRecipient.addItem(executeQueryNameAccounts.getString("sName"));
            }
            executeQueryNameAccounts.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insert(String nAccountSenderId, String nAccountRecipientId, Integer dSum, String UserId) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String insertSQLString = ("insert into t_dicTransfers(nUserId, nAccountSenderId, nAccountRecipientId, dSum)" +
                    " values" +
                    "('%1$s'," +
                    "(select nId from t_dicAccounts where sName='%2$s')," +
                    "(select nId from t_dicAccounts where sName='%3$s')," +
                    " '%4$s')");
            String insertSQL = String.format(insertSQLString, UserId, nAccountSenderId, nAccountRecipientId, dSum);
            stmt.executeUpdate(insertSQL);

            if (filterId < nId) {
                filterId = nId;
            }
            ResultSet executeQuery = stmt.executeQuery(String.format(sqlSelectTransfers, UserId, filterId));
            System.out.println();
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                nAccountSender = executeQuery.getString("AccountSender");
                String nAccountRecipient = executeQuery.getString("AccountRecipient");
                dSum = executeQuery.getInt("dSum");
                Transfers.add(new Transfers(nId, nAccountSender, nAccountRecipient, dSum));
                filterId = nId;
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void delete(Object value, int selectedRows) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicTransfers where nId=%1$s");
            String insertSQL = String.format(insertSQLString, value);
            stmt.executeUpdate(insertSQL);
            Transfers.remove(selectedRows);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update(String nAccountSenderId, String nAccountRecipientId, Integer dSum, String value, String userId) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            if (value != null) {
                currentTransferId = value;
            }
            String insertSQLString = ("update t_dicTransfers set  nAccountSenderId=(select nId from t_dicAccounts where sName='%1$s'), nAccountRecipientId=(select nId from t_dicAccounts where sName='%2$s'), dSum=%3$s where nId=%4$s");
            String insertSQL = String.format(insertSQLString, nAccountSenderId, nAccountRecipientId, dSum, currentTransferId);
            stmt.executeUpdate(insertSQL);
            Transfers.removeAll(Transfers);
            ResultSet executeQuery = stmt.executeQuery(String.format(sqlSelectTransfers, userId, 0));
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                nAccountSenderId = executeQuery.getString("AccountSender");
                nAccountRecipientId = executeQuery.getString("AccountRecipient");
                dSum = executeQuery.getInt("dSum");
                Transfers.add(new Transfers(nId, nAccountSenderId, nAccountRecipientId, dSum));
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void groupBalanceTransfer(String userId, String account) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String balanceSQLString = "select sum(dSum) as Sum from t_dicTransfers t  where nUserId=%2$s and %1$s=(select nId from t_dicAccounts where sName='%3$s') group by %1$s";
            ResultSet expenseExecuteQuery = stmt.executeQuery(String.format(balanceSQLString, "nAccountSenderId", userId, account));
            while (expenseExecuteQuery.next()) {
                expenseTransiction = expenseExecuteQuery.getLong("Sum");
            }
            ResultSet profitExecuteQuery = stmt.executeQuery(String.format(balanceSQLString, "nAccountRecipientId", userId, account));
            while (profitExecuteQuery.next()) {
                profitTransiction = profitExecuteQuery.getLong("Sum");
            }
            balanceTransiction = profitTransiction - expenseTransiction;
            expenseTransiction = 0;
            profitTransiction = 0;

            expenseExecuteQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}