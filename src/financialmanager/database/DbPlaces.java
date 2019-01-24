package financialmanager.database;

import financialmanager.data.Places;
import financialmanager.gui.AddPlace;
import financialmanager.gui.WindowPlaces;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.gui.UpdatePlace.textFieldAddress;
import static financialmanager.gui.UpdatePlace.textFieldName;
import static financialmanager.gui.WindowPlaces.modelPlaces;

public class DbPlaces {
    public static Integer nId;
    public static String sName;
    public static ArrayList<Places> places;
    public static String sEmail;
    public static ArrayList<String> sEmailList;
    private static String connectionString;
    public static int filternId = 0;
    static String currentPlaceId = "0";

    public static void connect() {
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
        connectionString = String.format(connectionUrl, instanceName, databaseName, userName, pass);
        //    Vector<Vector<Object>> retVector = new Vector<Vector<Object>>();
        //     Vector<Object> newRow = null;
    }

    public static void view() {
        connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicPlaces"
            );
            // Обход результатов выборки
            places = new ArrayList<>();
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                sName = executeQuery.getString("sName");
                String sAddress = executeQuery.getString("sAddress");
                places.add(new Places(nId, sName, sAddress));
            }
            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbPlaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void add() {
        connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String Name = AddPlace.textFieldName.getText();
            String Address = AddPlace.textFieldAddress.getText();
            String insertSQLString = ("insert into t_dicPlaces(sName, sAddress) values ('%1$s','%2$s')");
            String insertSQL = String.format(insertSQLString, Name, Address);
            stmt.executeUpdate(insertSQL);

            if (filternId < DbPlaces.nId) {
                filternId = DbPlaces.nId;
            }
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicPlaces where nId>" + filternId
            );
            // Обход результатов выборки
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                ;
                String sName = executeQuery.getString("sName");
                String sAddress = executeQuery.getString("sAddress");
                places.add(new Places(nId, sName, sAddress));
                modelPlaces.fireTableDataChanged();
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
        connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicPlaces where nId=%1$s");
            String insertSQL = String.format(insertSQLString, WindowPlaces.result);
            stmt.executeUpdate(insertSQL);
            places.remove(WindowPlaces.selectedRows[WindowPlaces.i - 1]);
            modelPlaces.fireTableDataChanged();
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
        connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String Name = textFieldName.getText();
            String Address = textFieldAddress.getText();
            if (String.valueOf(WindowPlaces.model.getValueAt(WindowPlaces.selIndex, 0)) != null) {
                currentPlaceId = String.valueOf(WindowPlaces.model.getValueAt(WindowPlaces.selIndex, 0));
            }
            String insertSQLString = ("update t_dicPlaces set  sName='%1$s', sAddress='%2$s' where nId=%3$s");
            String insertSQL = String.format(insertSQLString, Name, Address, currentPlaceId);
            stmt.executeUpdate(insertSQL);
            places.removeAll(places);
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicPlaces"
            );
            // Обход результатов выборки
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                String sName = executeQuery.getString("sName");
                String sAdress = executeQuery.getString("sAddress");
                places.add(new Places(nId, sName, sAdress));
                modelPlaces.fireTableDataChanged();
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