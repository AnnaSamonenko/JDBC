package dao;

import entities.Human;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HumanDAO implements AutoCloseable {

    private Connection connection;
    private static String sqlInsert = "INSERT INTO $table_name(human_name, human_surname) VALUES(?, ?)";
    private static String sqlSelect = "SELECT * FROM $table_name";

    public HumanDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(String tableName, Human human) {
        try (PreparedStatement st = connection.prepareStatement(sqlInsert.replace("$table_name", tableName))) {
            st.setString(1, human.getName());
            st.setString(2, human.getSurname());
            st.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Human> getAllHumans(String tableName) {
        List<Human> humans = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            try (ResultSet resultSet = st.executeQuery(sqlSelect.replace("$table_name", tableName))) {
                while (resultSet.next()) {
                    Human human = new Human();
                    human.setId(resultSet.getInt("human_id"));
                    human.setName(resultSet.getString("human_name"));
                    human.setSurname(resultSet.getString("human_surname"));
                    humans.add(human);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return humans;
    }

    public void removeAllRecords(String tableName) {

    }

    @Override
    public void close() throws SQLException {
        if (connection != null)
            connection.close();
    }
}
