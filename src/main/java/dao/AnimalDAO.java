package dao;

import entities.Animal;
import entities.Human;

import java.sql.*;
import java.util.*;

public class AnimalDAO implements DAO {

    private Connection connection;
    private static String sqlInsert = "INSERT INTO $table_name(alias, owner_id) VALUES(?, ?)";
    private static String sqlDelete = "DELETE FROM $table_name";
    private static String sqlJoin = "SELECT human_id, human_name, human_surname, animal_id, alias FROM $human_table " +
            "INNER JOIN $animal_table ON $animal_table.owner_id = $human_table.human_id;";

    private static String sqlJoinWithWhere = "SELECT human_id, human_name, human_surname, animal_id, alias FROM $human_table " +
            "INNER JOIN $animal_table ON $animal_table.owner_id = $human_table.human_id WHERE animal_id = ?;";

    public AnimalDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Animal get(String tableNameAnimal, String tableNameHuman, int id) {
        Animal animal = new Animal();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlJoinWithWhere.replace("$human_table", tableNameHuman).replace("$animal_table", tableNameAnimal))) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                animal.setId(rs.getInt("animal_id"));
                animal.setAlias(rs.getString("alias"));
                animal.setHuman(new Human(rs.getInt("human_id"), rs.getString("human_name"), rs.getString("human_surname")));
            }
        } catch (SQLException ex) {
        }
        return animal;
    }

    public List<Animal> getAll(String tableNameAnimal, String tableNameHuman) {
        List<Animal> animals = new ArrayList<>();
        Map<Integer, Human> mapper = new HashMap<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(sqlJoin.replace("$human_table", tableNameHuman).replace("$animal_table", tableNameAnimal))) {
                while (rs.next()) {
                    Animal animal = new Animal();
                    int animalId = rs.getInt("animal_id");
                    String alias = rs.getString("alias");
                    int humanId = rs.getInt("human_id");
                    String humanName = rs.getString("human_name");
                    String humanSurname = rs.getString("human_surname");

                    animal.setId(animalId);
                    animal.setAlias(alias);

                    Human value = mapper.get(humanId);
                    if (value == null) {
                        value = new Human(humanId, humanName, humanSurname);
                        mapper.put(humanId, value);
                    }
                    value.addAnimal(animal);
                    animal.setHuman(value);
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
