package financialmanager.database;

import financialmanager.data.Users;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static financialmanager.database.DbUsers.users;
import financialmanager.gui.User.*;
import static financialmanager.gui.WindowUsers.modelUsers;

public class User {
    public static int filternId = 0;

    public void main() {
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
    //    Vector<Vector<Object>> retVector = new Vector<Vector<Object>>();
    //     Vector<Object> newRow = null;
        try {
        // Подключение к базе данных
        Connection con = DriverManager.getConnection(connectionString);
        // Отправка запроса на выборку и получение результатов
        Statement stmt = con.createStatement();
        String Surname = new String(financialmanager.gui.User.textFieldSurname.getText());
        String Name = new String(financialmanager.gui.User.textFieldName.getText());
        String Birthday = new String(textFieldBirthday.getText());
        String Sex = new String(textFieldSex.getText());
        String Phone = new String(textFieldPhone.getText());
        String Email = new String(textFieldEmail.getText());
        String insertSQLString = ("insert into t_dicUsers( sSurname ,sName, dtBirthday, sSex, sPhone, sEmail) values ('%1$s','%2$s','%3$s','%4$s','%5$s','%6$s')");
        String insertSQL = String.format(insertSQLString, Surname, Name, Birthday, Sex, Phone, Email);
        // System.out.println(insertSQL);
        stmt.executeUpdate(insertSQL);

        if (filternId < DbUsers.nId) {
            filternId = DbUsers.nId;
        }
        System.out.println(filternId);
        ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                "FROM t_dicUsers where nId>" + filternId
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
            filternId = nId;
        }

        stmt.close();
        con.close();
    } catch (
    SQLException ex) {
        // Обработка исключений
        Logger.getLogger(DbExpenses.class.getName()).log(Level.SEVERE, null, ex);
    }}
}
