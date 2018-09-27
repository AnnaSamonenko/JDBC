package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnection {

    private MySQLDatabaseConnection() {
    }

    public static Connection getConnection(String url, String name, String password,
                                           String databaseName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url + "/" + databaseName, name, password);
    }

}
