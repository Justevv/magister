package financialmanager.database;

import financialmanager.data.Users;
import financialmanager.gui.WindowUsers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbUsers.users;
import static financialmanager.gui.UpdateUser.*;
import static financialmanager.gui.WindowUsers.modelUsers;

public class UpdateUser {
    static String currentUserId = "0";

    public static void main() {
        boolean containsName = false;
        for (String sEmailList : DbUsers.sEmailList) {
            if (sEmailList.equals(textFieldEmail.getText())) {
                containsName = true;
                System.out.println("Такой пользователь уже есть");
            }
        }
        if (containsName == false) {
            // Формирование строки подключения
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
                String Surname = new String(textFieldSurname.getText());
                String Name = new String(textFieldName.getText());
                String Birthday = new String(textFieldBirthday.getText());
                String Sex = new String(textFieldSex.getText());
                String Phone = new String(textFieldPhone.getText());
                String Email = new String(textFieldEmail.getText());
                DbUsers.sEmailList.add(Email);
                if (String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 0)) != null) {
                    currentUserId = String.valueOf(WindowUsers.model.getValueAt(WindowUsers.selIndex, 0));
                }
                String insertSQLString = ("update t_dicUsers set  sSurname ='%1$s',sName='%2$s', dtBirthday='%3$s', sSex='%4$s', sPhone='%5$s', sEmail='%6$s'where nId=%7$s");
                String insertSQL = String.format(insertSQLString, Surname, Name, Birthday, Sex, Phone, Email, currentUserId);
                stmt.executeUpdate(insertSQL);

                users.removeAll(users);
                ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                        "FROM t_dicUsers"
                );
                // Обход результатов выборки
                while (executeQuery.next()) {
                    int nId = executeQuery.getInt("nId");
                    String sSurname = executeQuery.getString("sSurname");
                    String sName = executeQuery.getString("sName");
                    String dtBirthday = executeQuery.getString("dtBirthday");
                    String sSex = executeQuery.getString("sSex");
                    String sPhone = executeQuery.getString("sPhone");
                    String sEmail = executeQuery.getString("sEmail");
                    users.add(new Users(nId, sSurname, sName, dtBirthday, sSex, sPhone, sEmail));
                    modelUsers.fireTableDataChanged();
                }

                stmt.close();
                con.close();
            } catch (
                    SQLException ex) {
                // Обработка исключений
                Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
