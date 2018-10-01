package dao;

import entities.Animal;
import entities.Human;

import java.sql.*;
import java.util.*;

public class HumanDAO implements DAO {

    private Connection connection;
    private static String sqlInsert = "INSERT INTO $table_name(human_name, human_surname) VALUES(?, ?)";
    private static String sqlSelectById = "SELECT * FROM table_name WHERE human_id = ?";
    private static String sqlSelect = "SELECT * FROM $table_name";
    private static String sqlDelete = "DELETE FROM $table_name";
    private static String sqlJoin = "SELECT human_id, human_name, human_surname, animal_id, alias FROM $human_table " +
            "LEFT JOIN $animal_table ON $animal_table.owner_id = $human_table.human_id;";

    public HumanDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Human get(String tableNameHuman, String tableNameAnimal, int id) {
        Human human = new Human();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectById.replace("$table_name", tableNameHuman))) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                human.setId(rs.getInt("human_id"));
                human.setName(rs.getString("human_name"));
                human.setSurname(rs.getString("human_surname"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return human;
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

    public List<Human> getAll(String tableNameHuman, String tableNameAnimal) {
        Map<Integer, Human> mapper = new HashMap<>();
        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(sqlJoin.replace("$human_table", tableNameHuman).replace("$animal_table", tableNameAnimal))) {
                while (rs.next()) {
                    int animalId = rs.getInt("animal_id");
                    String alias = rs.getString("alias");
                    int humanId = rs.getInt("human_id");
                    String humanName = rs.getString("human_name");
                    String humanSurname = rs.getString("human_surname");

                    Human human = mapper.get(humanId);
                    if (human == null) {
                        human = new Human(humanId, humanName, humanSurname);
                        mapper.put(humanId, human);
                    }
                    if (animalId != 0) {
                        Animal animal = new Animal(animalId, alias);
                        animal.setHuman(human);
                        human.addAnimal(animal);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>(mapper.values());
    }

    public void removeAll(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlDelete.replace("$table_name", tableName));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
