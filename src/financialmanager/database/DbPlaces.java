package financialmanager.database;

import financialmanager.data.Places;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbPlaces {
    private DbConnect dbConnect = new DbConnect();
    private String currentPlaceId = "0";

    public List<Places> select() {
        List<Places> places = new ArrayList<>();
        try {
            Statement stmt = dbConnect.connect();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicPlaces"
            );
            while (executeQuery.next()) {
                int id = executeQuery.getInt("nId");
                String name = executeQuery.getString("sName");
                String address = executeQuery.getString("sAddress");
                places.add(new Places(id, name, address));
            }
            executeQuery.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbPlaces.class.getName()).log(Level.SEVERE, null, ex);
        }
        return places;
    }

    public void insert(String name, String address) {
        try {
            Statement stmt = dbConnect.connect();
            String insertSQLString = ("insert into t_dicPlaces(sName, sAddress) values ('%1$s','%2$s')");
            String insertSQL = String.format(insertSQLString, name, address);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Object value) {
        try {
            Statement stmt = dbConnect.connect();
            String insertSQLString = ("delete from t_dicPlaces where nId=%1$s");
            String insertSQL = String.format(insertSQLString, value);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String name, String address, String value) {
        try {
            Statement stmt = dbConnect.connect();
            if (value != null) {
                currentPlaceId = value;
            }
            String insertSQLString = ("update t_dicPlaces set  sName='%1$s', sAddress='%2$s' where nId=%3$s");
            String insertSQL = String.format(insertSQLString, name, address, currentPlaceId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}