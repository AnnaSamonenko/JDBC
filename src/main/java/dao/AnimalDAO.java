package dao;

import entities.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO implements DAO {

    private Connection connection;
    private static String sqlSelect = "SELECT * from $table_name";
    private static String sqlSelectById = "SELECT * from $table_name WHERE human_id = ?";
    private static String sqlInsert = "INSERT INTO $table_name(alias, owner_id) VALUES(?, ?)";
    private static String sqlDelete = "DELETE FROM $table_name";

    public AnimalDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Animal get(String tableNameAnimal, String tableNameHuman, int id) {
        HumanDAO humanDAO = new HumanDAO(connection);

        Animal animal = new Animal();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectById.replace("$table_name", tableNameAnimal))) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                animal.setId(resultSet.getInt("animal_id"));
                animal.setAlias(resultSet.getString("alias"));
                animal.setHuman(humanDAO.get(tableNameHuman, tableNameAnimal, resultSet.getInt("owner_id")));
            }
        } catch (SQLException ex) {
        }
        return animal;
    }

    public List<Animal> getAll(String tableNameAnimal, String tableNameHuman) {
        HumanDAO humanDAO = new HumanDAO(connection);
        List<Animal> animals = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sqlSelect.replace("$table_name", tableNameAnimal))) {
                while (resultSet.next()) {
                    Animal animal = new Animal();
                    animal.setId(resultSet.getInt("animal_id"));
                    animal.setAlias(resultSet.getString("alias"));
                    animal.setHuman(humanDAO.get(tableNameHuman, tableNameAnimal, resultSet.getInt("owner_id")));
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
            st.setInt(2, animal.getHuman().getId());
            st.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeAll(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlDelete.replace("$table_name", tableName));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
