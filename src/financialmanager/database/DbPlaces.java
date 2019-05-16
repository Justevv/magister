package financialmanager.database;

import financialmanager.data.Places;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbConnect.connectionString;

public class DbPlaces {
    private Integer nId = 0;
    private String sName;
    public static ArrayList<Places> places = new ArrayList<>();
    String currentPlaceId = "0";

    public void select() {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicPlaces"
            );
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                sName = executeQuery.getString("sName");
                String sAddress = executeQuery.getString("sAddress");
                places.add(new Places(nId, sName, sAddress));
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbPlaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(String name, String address) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String insertSQLString = ("insert into t_dicPlaces(sName, sAddress) values ('%1$s','%2$s')");
            String insertSQL = String.format(insertSQLString, name, address);
            stmt.executeUpdate(insertSQL);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Object value, int selectedRows) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            String insertSQLString = ("delete from t_dicPlaces where nId=%1$s");
            String insertSQL = String.format(insertSQLString, value);
            stmt.executeUpdate(insertSQL);
            places.remove(selectedRows);
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String name, String address, String value) {
        DbConnect.connect();
        try {
            Connection con = DriverManager.getConnection(connectionString);
            Statement stmt = con.createStatement();
            if (value != null) {
                currentPlaceId = value;
            }
            String insertSQLString = ("update t_dicPlaces set  sName='%1$s', sAddress='%2$s' where nId=%3$s");
            String insertSQL = String.format(insertSQLString, name, address, currentPlaceId);
            stmt.executeUpdate(insertSQL);
            places.removeAll(places);
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicPlaces"
            );
            while (executeQuery.next()) {
                int nId = executeQuery.getInt("nId");
                String sName = executeQuery.getString("sName");
                String sAdress = executeQuery.getString("sAddress");
                places.add(new Places(nId, sName, sAdress));
            }
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}