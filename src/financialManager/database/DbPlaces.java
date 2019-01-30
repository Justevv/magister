package financialManager.database;

import financialManager.data.Places;
import financialManager.gui.Place;
import financialManager.gui.WindowPlaces;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialManager.database.DbConnect.connectionString;
import static financialManager.gui.Place.textFieldAddress;
import static financialManager.gui.Place.textFieldName;
import static financialManager.gui.WindowPlaces.modelPlaces;

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
        DbConnect.connect();
        try {
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            String Name = Place.textFieldName.getText();
            String Address = Place.textFieldAddress.getText();
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
        DbConnect.connect();
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
        DbConnect.connect();
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