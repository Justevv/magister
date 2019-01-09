package financialmanager.database;

import financialmanager.gui.OpenWindow;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbExpenses.expenses;
import static financialmanager.gui.WindowExpenses.tModel;

public class Expenses {
    private static JTextField textFieldDate = new JTextField("20181010", 5);
    private static JTextField textFieldSum = new JTextField("100", 5);
    public static int filternId = 50000;
    static String Place;
    static String PaymentType;
    static String Category;

    public static void main() {
        Place = (String) financialmanager.gui.Expenses.comboBoxPlace.getSelectedItem();
        PaymentType = (String) financialmanager.gui.Expenses.comboBoxPaymentType.getSelectedItem();
        Category = (String) financialmanager.gui.Expenses.comboBoxCategory.getSelectedItem();
        String computername = null;
        try {
            computername = InetAddress.getLocalHost().getHostName();
        } catch (
                UnknownHostException e) {
            e.printStackTrace();
        }
        String instanceName = computername + "\\SQLEXPRESS";
        String databaseName = "expenses";
        String userName = "supertest";
        String pass = "supertest";
        String connectionUrl = "jdbc:sqlserver://%1$s;databaseName=%2$s;user=%3$s;password=%4$s;";
        String connectionString = String.format(connectionUrl, instanceName, databaseName, userName, pass);
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String dtDate = new String(textFieldDate.getText());
            Integer dSum = new Integer(textFieldSum.getText());
            String insertSQLString = ("insert into t_Expenses( dtDate ,dSum,nUserId, nCategoryId ,nPlaceId,nPaymentTypeId) " +
                    "values " +
                    "('%1$s',%2$s,%3$s," +
                    "(select nId from [dbo].[t_dicCategories] where sName='%4$s')" +
                    ",(select nId from [dbo].[t_dicPlaces] where sName='%5$s')" +
                    ",(select nId from [dbo].[t_dicPaymentTypes] where sName='%6$s'))");
            String insertSQL = String.format(insertSQLString, dtDate, dSum, OpenWindow.userLogin, Category, Place, PaymentType);
            // System.out.println(insertSQL);
            stmt.executeUpdate(insertSQL);
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
                tModel.fireTableDataChanged();
                filternId = nId;
//                     System.out.println("Down " + filternId);
            }
            ResultSet executeQuery2 = stmt.executeQuery("select sum(dSum) as dSum, count(dSum) as dCount from t_Expenses where nUserId=" + OpenWindow.userLogin);
            while (executeQuery2.next()) {
                long balance = executeQuery2.getLong("dSum");
                Integer dCount = executeQuery2.getInt("dCount");
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
}
