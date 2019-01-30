package financialmanager.database;

import financialmanager.data.Places;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbPlaces {
    public static Integer nId;
    public static String sName;
    public static ArrayList<Places> places;
    public static int filternId = 0;
    static String currentPlaceId = "0";

     public static void view() {
         DbConnect.connect();
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
                sName = executeQuery.getString("nAccountSender");
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

    public static void add(String name,String address) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String insertSQLString = ("insert into t_dicPlaces(nAccountSender, sAddress) values ('%1$s','%2$s')");
            String insertSQL = String.format(insertSQLString, name, address);
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
                String sName = executeQuery.getString("nAccountSender");
                String sAddress = executeQuery.getString("sAddress");
                places.add(new Places(nId, sName, sAddress));
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
            String insertSQLString = ("delete from t_dicPlaces where nId=%1$s");
            String insertSQL = String.format(insertSQLString, value);
            stmt.executeUpdate(insertSQL);
            places.remove(selectedRows);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update(String name, String address, String value) {
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            if (value != null) {
                currentPlaceId = value;
            }
            String insertSQLString = ("update t_dicPlaces set  nAccountSender='%1$s', sAddress='%2$s' where nId=%3$s");
            String insertSQL = String.format(insertSQLString, name, address, currentPlaceId);
            stmt.executeUpdate(insertSQL);
            places.removeAll(places);
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicPlaces"
            );
            // Обход результатов выборки
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                String sName = executeQuery.getString("nAccountSender");
                String sAdress = executeQuery.getString("sAddress");
                places.add(new Places(nId, sName, sAdress));
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