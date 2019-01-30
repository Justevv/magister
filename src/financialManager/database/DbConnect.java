package financialManager.database;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DbConnect {
    static String connectionString;

    public static void connect()    {
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
        connectionString = String.format(connectionUrl, instanceName, databaseName, userName, pass);
    }
}