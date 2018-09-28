package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnection implements AutoCloseable {

    private static Connection connection;

    private MySQLDatabaseConnection() {
    }

    public static Connection getConnection(String url, String name, String password,
                                           String databaseName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url + "/" + databaseName, name, password);
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
