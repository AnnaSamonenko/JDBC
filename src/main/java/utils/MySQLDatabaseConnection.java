package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnection {

    private MySQLDatabaseConnection() {
    }

    public static Connection getConnection(String url, String name, String password, String databaseName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url + "/" + databaseName, name, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Cannot find driver: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Incorrect value of params for getConnection() : " + ex.getMessage());
        }
        return null;
    }

}
