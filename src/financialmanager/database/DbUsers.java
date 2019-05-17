package financialmanager.database;

import financialmanager.data.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUsers {
    private DbConnect dbConnect = new DbConnect();
    public String sName;
    public static ArrayList<Users> users = new ArrayList<>();
    public String sEmail;
    public ArrayList<String> emailList = new ArrayList<>();
    static String currentUserId = "0";

    public void select() {
        users.removeAll(users);
        try {
            Statement stmt = dbConnect.connect();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicUsers"
            );
            while (executeQuery.next()) {
                int id = executeQuery.getInt("nId");
                String sSurname = executeQuery.getString("sSurname");
                sName = executeQuery.getString("sName");
                String dtBirthday = executeQuery.getString("dtBirthday");
                String sSex = executeQuery.getString("sSex");
                String sPhone = executeQuery.getString("sPhone");
                sEmail = executeQuery.getString("sEmail");
                users.add(new Users(id, sSurname, sName, dtBirthday, sSex, sPhone, sEmail));
                emailList.add(sEmail);
            }
            executeQuery.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(String surname, String name, String birthday, String sex, String phone, String email) {
        boolean containsName = false;
        for (String emailList : emailList) {
            if (emailList.equals(email)) {
                containsName = true;
                System.out.println("Такой пользователь уже есть");
            }
        }
        if (containsName == false) {
            try {
                Statement stmt = dbConnect.connect();
                String insertSQLString = ("insert into t_dicUsers( sSurname ,sName, dtBirthday, sSex, sPhone, sEmail) values ('%1$s','%2$s','%3$s','%4$s','%5$s','%6$s')");
                String insertSQL = String.format(insertSQLString, surname, name, birthday, sex, phone, email);
                stmt.executeUpdate(insertSQL);
                stmt.close();
            } catch (
                    SQLException ex) {
                Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void delete(String nId) {
        try {
            Statement stmt = dbConnect.connect();
            String insertSQLString = ("delete from t_dicUsers where nId=%1$s");
            String insertSQL = String.format(insertSQLString, nId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String Surname, String Name, String Birthday, String Sex, String Phone, String Email, String Id, String currentEmail) {
        boolean containsName = false;
        for (String sEmailList : emailList) {
            if (sEmailList.equals(Email)) {
                containsName = true;
            }
        }
        if (currentEmail.equals(Email) != true) {
            if (containsName == false) {
                updateGo(Surname, Name, Birthday, Sex, Phone, Email, Id);
            } else {
                System.out.println("Такой пользователь уже есть");
            }
        } else {
            updateGo(Surname, Name, Birthday, Sex, Phone, Email, Id);
        }
    }

    public void updateGo(String Surname, String Name, String Birthday, String Sex, String Phone, String Email, String Id) {
       try {
           Statement stmt = dbConnect.connect();
            emailList.add(Email);
            if (Id != null) {
                currentUserId = Id;
            }
            String insertSQLString = ("update t_dicUsers set  sSurname ='%1$s',sName='%2$s', dtBirthday='%3$s', sSex='%4$s', sPhone='%5$s', sEmail='%6$s'where nId=%7$s");
            String insertSQL = String.format(insertSQLString, Surname, Name, Birthday, Sex, Phone, Email, currentUserId);
            stmt.executeUpdate(insertSQL);
            stmt.close();
        } catch (
                SQLException ex) {
            Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}