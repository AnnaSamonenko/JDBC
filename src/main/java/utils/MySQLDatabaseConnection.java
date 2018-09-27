package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnection implements AutoCloseable {

    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    public static Connection getConnection(String databaseName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL + databaseName, NAME, PASSWORD);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed1 : " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Database Connection Creation Failed2 : " + ex.getMessage());
        }
        return connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
