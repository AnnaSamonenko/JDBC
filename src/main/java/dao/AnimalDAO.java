package dao;

import entities.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO implements AutoCloseable {

    private Connection connection;
    private static String sqlSelect = "SELECT * from $table_name";
    private static String sqlInsert = "INSERT INTO $table_name(alias, owner_id) VALUES(?, ?)";
    private static String sqlDelete = "DELETE FROM $table_name";

    public AnimalDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Animal> getAllRecords(String tableName) {
        List<Animal> animals = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sqlSelect.replace("$table_name", tableName))) {
                while (resultSet.next()) {
                    Animal animal = new Animal();
                    animal.setId(resultSet.getInt("animal_id"));
                    animal.setAlias(resultSet.getString("alias"));
                    animal.setHuman(resultSet.getInt("owner_id"));
                    animals.add(animal);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return animals;
    }

    public void create(String tableName, Animal animal) {
        try (PreparedStatement st = connection.prepareStatement(sqlInsert.replace("$table_name", tableName))) {
            st.setString(1, animal.getAlias());
            st.setInt(2, animal.getHuman());
            st.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeAllRecords(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlDelete.replace("$table_name", tableName));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
