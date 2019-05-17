package financialmanager.database;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbExpenses {
    private DbConnect dbConnect = new DbConnect();
    private Integer nId = 0;
    private Integer dSum;
    public static ArrayList<financialmanager.data.Expenses> expenses = new ArrayList<>();
    public static Long expense;
    public static Long profit;
    public String nUserSurname;
    public static long profitCategory;
    public static long expenseCategory;
    public static long balanceCategory;
    private String sqlSelect;

    public void select(String userId) {
        expenses.removeAll(expenses);
        try {
            Statement stmt = dbConnect.connect();
            sqlSelect = "SELECT e.nId as nId" +
                    ",dtDate, sSurname" +
                    ",c.sName as CategoriesName" +
                    ",p.sName as nPlaceName" +
                    ",pt.sName as nPaymentTypeName" +
                    ",a.sName as nAccount" +
                    ",tt.sName as nTransactionType" +
                    ", dSum " +
                    "FROM t_Expenses e join t_dicUsers u on e.nUserId=u.nId " +
                    "join t_dicCategories c on e.nCategoryId=c.nId " +
                    "join t_dicPlaces p on e.nPlaceId=p.nId " +
                    "join t_dicPaymentTypes pt on e.nPaymentTypeId=pt.nId " +
                    "join t_dicAccounts a on e.nAccountId=a.nId " +
                    "join t_dicTransactionTypes tt on e.nTransactionTypeId=tt.nId " +
                    "where e.nUserId='%1$s'" +
                    "and e.nId>'%2$s'";
            ResultSet executeQuery = stmt.executeQuery(String.format(sqlSelect, userId, 0));
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                String dtDate = executeQuery.getString("dtDate");
                nUserSurname = executeQuery.getString("sSurname");
                String nCategoryName = executeQuery.getString("CategoriesName");
                String nPlaceName = executeQuery.getString("nPlaceName");
                String nPaymentTypeName = executeQuery.getString("nPaymentTypeName");
                String nAccount = executeQuery.getString("nAccount");
                String nTransactionType = executeQuery.getString("nTransactionType");
                dSum = executeQuery.getInt("dSum");
                expenses.add(new financialmanager.data.Expenses(nId, dtDate, nUserSurname, nCategoryName, nPlaceName, nPaymentTypeName, dSum, nAccount, nTransactionType));
            }
            ResultSet executeQueryExpense = stmt.executeQuery("select sum(dSum) as Expense, count(dSum) as dCount from t_Expenses where nTransactionTypeId=2 and nUserId=" + userId);
            while (executeQueryExpense.next()) {
                expense = executeQueryExpense.getLong("Expense");
                Integer dCount = executeQueryExpense.getInt("dCount");
            }

            ResultSet executeQueryProfit = stmt.executeQuery("select sum(dSum) as Profit, count(dSum) as dCount from t_Expenses where  nTransactionTypeId=1 and nUserId=" + userId);
            while (executeQueryProfit.next()) {
                profit = executeQueryProfit.getLong("Profit");
                Integer dCount = executeQueryProfit.getInt("dCount");
            }

            executeQuery.close();
            executeQueryExpense.close();
            executeQueryProfit.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void comboBoxRead(JComboBox comboBoxPlace, JComboBox comboBoxPaymentType, JComboBox comboBoxCategory, JComboBox comboBoxAccount, JComboBox comboBoxTransactionType) {
               try {
                   Statement stmt = dbConnect.connect();
            ResultSet executeQueryNamePlaces = stmt.executeQuery("select * from t_dicPlaces");
            while (executeQueryNamePlaces.next()) {
                comboBoxPlace.addItem(executeQueryNamePlaces.getString("sName"));
            }
            ResultSet executeQueryNamePaymentTypes = stmt.executeQuery("select * from t_dicPaymentTypes");
            while (executeQueryNamePaymentTypes.next()) {
                comboBoxPaymentType.addItem(executeQueryNamePaymentTypes.getString("sName"));
            }
            ResultSet executeQueryNameCategories = stmt.executeQuery("select * from t_dicCategories");
            while (executeQueryNameCategories.next()) {
                comboBoxCategory.addItem(executeQueryNameCategories.getString("sName"));
            }
            ResultSet executeQueryNameAccounts = stmt.executeQuery("select * from t_dicAccounts");
            while (executeQueryNameAccounts.next()) {
                comboBoxAccount.addItem(executeQueryNameAccounts.getString("sName"));
            }
            ResultSet executeQueryNameTransactionTypes = stmt.executeQuery("select * from t_dicTransactionTypes");
            while (executeQueryNameTransactionTypes.next()) {
                comboBoxTransactionType.addItem(executeQueryNameTransactionTypes.getString("sName"));
            }
            executeQueryNamePlaces.close();
            executeQueryNamePaymentTypes.close();
            executeQueryNameCategories.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(String userId, String place, String paymentType, String category, String account, String transactionType, String dtDate, Integer dSum) {
               try {
                   Statement stmt = dbConnect.connect();
            String insertSQLString = ("insert into t_Expenses( dtDate ,dSum,nUserId, nCategoryId ,nPlaceId,nPaymentTypeId, nAccountId, nTransactionTypeId) " +
                    "values " +
                    "('%1$s',%2$s,%3$s," +
                    "(select nId from [dbo].[t_dicCategories] where sName='%4$s')" +
                    ",(select nId from [dbo].[t_dicPlaces] where sName='%5$s')" +
                    ",(select nId from [dbo].[t_dicPaymentTypes] where sName='%6$s')" +
                    ",(select nId from [dbo].[t_dicAccounts] where sName='%7$s')" +
                    ",(select nId from [dbo].[t_dicTransactionTypes] where sName='%8$s')" +
                    ")");
            String insertSQL = String.format(insertSQLString, dtDate, dSum, userId, category, place, paymentType, account, transactionType);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Object idExpenses) {
        try {
            Statement stmt = dbConnect.connect();
            String insertSQLString = ("delete from t_Expenses where nId=%1$s");
            String insertSQL = String.format(insertSQLString, idExpenses);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String userId, String place, String paymentType, String category, String account, String transactionType, String dtDate, Integer dSum, Object value) {
        try {
            Statement stmt = dbConnect.connect();
            if (String.valueOf(value) != null) {
            }
            String insertSQLString = ("update t_Expenses set  dtDate='%1$s', dSum='%2$s', nUserId='%3$s'" +
                    ", nCategoryId=(select nId from [dbo].[t_dicCategories] where sName='%4$s')" +
                    ", nPlaceId=(select nId from [dbo].[t_dicPlaces] where sName='%5$s')" +
                    ", nPaymentTypeId=(select nId from [dbo].[t_dicPaymentTypes] where sName='%6$s')" +
                    ", nAccountId=(select nId from [dbo].[t_dicAccounts] where sName='%7$s')" +
                    ", nTransactionTypeId=(select nId from [dbo].[t_dicTransactionTypes] where sName='%8$s')" +
                    " where nId=%9$s");
            String insertSQL = String.format(insertSQLString, dtDate, dSum, userId, category, place, paymentType, account, transactionType, value);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void groupBalanceCategory(String userId, String category) {
        try {
            Statement stmt = dbConnect.connect();
            String balanceSQLString = "select sum(dSum) as Sum " +
                    "from t_Expenses e join t_dicCategories c on e.nCategoryId=c.nId " +
                    "where e.nCategoryId=(select nId from t_dicCategories where sName='%1$s') and e.nUserId=%2$s and e.nTransactionTypeId=%3$s";
            ResultSet profitExecuteQuery = stmt.executeQuery(String.format(balanceSQLString, category, userId, 1));
            while (profitExecuteQuery.next()) {
                profitCategory = profitExecuteQuery.getLong("Sum");
            }
            ResultSet ExpenseExecuteQuery = stmt.executeQuery(String.format(balanceSQLString, category, userId, 2));
            while (ExpenseExecuteQuery.next()) {
                expenseCategory = ExpenseExecuteQuery.getLong("Sum");
            }
            balanceCategory = profitCategory - expenseCategory;
            ExpenseExecuteQuery.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void groupBalanceAccount(String userId, String account) {
        try {
            Statement stmt = dbConnect.connect();
            String balanceSQLString = "select sum(dSum) as Sum " +
                    "from t_Expenses e join t_dicAccounts a on e.nCategoryId=a.nId " +
                    "where e.nAccountId=(select nId from t_dicAccounts where sName='%1$s') and e.nUserId=%2$s and e.nTransactionTypeId=%3$s";
            ResultSet profitExecuteQuery = stmt.executeQuery(String.format(balanceSQLString, account, userId, 1));
            while (profitExecuteQuery.next()) {
                profitCategory = profitExecuteQuery.getLong("Sum");
            }
            ResultSet ExpenseExecuteQuery = stmt.executeQuery(String.format(balanceSQLString, account, userId, 2));
            while (ExpenseExecuteQuery.next()) {
                expenseCategory = ExpenseExecuteQuery.getLong("Sum");
                balanceCategory = profitCategory - expenseCategory;
            }
            ExpenseExecuteQuery.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}