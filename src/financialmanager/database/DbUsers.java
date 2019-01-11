package financialmanager.database;

import financialmanager.data.Users;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUsers {
    public static Integer nId;
    public static String sName;
    public static ArrayList<Users> users;
    public static String sEmail;
    public static ArrayList<String> sEmailList;
  //  static String[] sEmailMas = new String[50];
//    public static int i = 0;

    public static void main(String[] args) {
        main();
    }

    public static void main() {  // Формирование строки подключения
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
            // Подключение к базе данных
            Connection con = DriverManager.getConnection(connectionString);
            // Отправка запроса на выборку и получение результатов
            Statement stmt = con.createStatement();
            ResultSet executeQuery = stmt.executeQuery("SELECT * " +
                    "FROM t_dicUsers"
            );
            // Обход результатов выборки
            users = new ArrayList<>();
            sEmailList = new ArrayList<>();
            while (executeQuery.next()) {
                nId = executeQuery.getInt("nId");
                String sSurname = executeQuery.getString("sSurname");
                sName = executeQuery.getString("sName");
                String dtBirthday = executeQuery.getString("dtBirthday");
                String sSex = executeQuery.getString("sSex");
                String sPhone = executeQuery.getString("sPhone");
                sEmail = executeQuery.getString("sEmail");
                users.add(new Users(nId, sSurname, sName, dtBirthday, sSex, sPhone, sEmail));
                sEmailList.add(sEmail);
//                sEmailMas[i] = sEmail;
//                i++;
            }

//            ResultSet executeQuery2 = stmt.executeQuery("select sum(dSum) as dSum, count(nId) as dCount from t_Expenses where nUserId=" + OpenWindow.userLogin);
//            while (executeQuery2.next()) {
//                balance = executeQuery2.getInt("dSum");
//                Integer dCount = executeQuery2.getInt("dCount");
//            }

            // Закрываем соединение
            executeQuery.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            // Обработка исключений
            Logger.getLogger(DbUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}