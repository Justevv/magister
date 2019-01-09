package financialmanager.database;

import financialmanager.gui.Expenses;
import financialmanager.gui.OpenWindow;
import financialmanager.gui.WindowExpenses;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbExpenses {
    public static Integer nId;
    public static Integer dSum;
    public static ArrayList<financialmanager.data.Expenses> expenses;
    public static Long balance;
    public static String nUserSurname;

    public static void main(String[] args) {
        main();
    }

    public static void main() {
        // Формирование строки подключения
        String computername = null;
        try {
            computername = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String instanceName = computername + "\\SQLEXPRESS";
        String databaseName = "expenses";
        String userName = "supertest";
        String pass = "supertest";
        String connectionUrl = "jdbc:sqlserver://%1$s;databaseName=%2$s;user=%3$s;password=%4$s;";
        String connectionString = String.format(connectionUrl, instanceName, databaseName, userName, pass);
        //    Vector<Vector<Object>> retVector = new Vector<Vector<Object>>();
        //     Vector<Object> newRow = null;
        try {
            Object[] deposit = null;
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
            Expenses.comboBoxPlace = new JComboBox();
            Expenses.comboBoxPaymentType = new JComboBox();
            Expenses.comboBoxCategory = new JComboBox();

            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                String dtDate = executeQuery.getString("dtDate");
                nUserSurname = executeQuery.getString("sSurname");
                String nCategoryName = executeQuery.getString("CategoriesName");
                String nPlaceName = executeQuery.getString("nPlaceName");
                String nPaymentTypeName = executeQuery.getString("nPaymentTypeName");
                dSum = executeQuery.getInt("dSum");
                expenses.add(new financialmanager.data.Expenses(nId, dtDate, nUserSurname, nCategoryName, nPlaceName, nPaymentTypeName, dSum));
                WindowExpenses.tModel.fireTableDataChanged();
            }
            ResultSet executeQuery2 = stmt.executeQuery("select sum(dSum) as dSum, count(dSum) as dCount from t_Expenses where nUserId=" + OpenWindow.userLogin
            );
            while (executeQuery2.next()) {
                balance = executeQuery2.getLong("dSum");
                Integer dCount = executeQuery2.getInt("dCount");
            }

            ResultSet executeQueryNamePlaces = stmt.executeQuery("select * from t_dicPlaces");
            while (executeQueryNamePlaces.next()) {
                Expenses.comboBoxPlace.addItem(executeQueryNamePlaces.getString("sName"));
            }

            ResultSet executeQueryNamePaymentTypes = stmt.executeQuery("select * from t_dicPaymentTypes");
            while (executeQueryNamePaymentTypes.next()) {
                Expenses.comboBoxPaymentType.addItem(executeQueryNamePaymentTypes.getString("sName"));
            }

            ResultSet executeQueryNameCategories = stmt.executeQuery("select * from t_dicCategories");
            while (executeQueryNameCategories.next()) {
                Expenses.comboBoxCategory.addItem(executeQueryNameCategories.getString("sName"));
            }

            // System.out.println(users.get(0));
            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}