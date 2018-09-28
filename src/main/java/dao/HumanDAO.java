package dao;

import entities.Human;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HumanDAO implements DAO {

    private Connection connection;
    private static String sqlInsert = "INSERT INTO $table_name(human_name, human_surname) VALUES(?, ?)";
    private static String sqlSelectById = "SELECT * FROM table_name WHERE human_id = ?";
    private static String sqlSelect = "SELECT * FROM $table_name";
    private static String sqlDelete = "DELETE FROM $table_name";

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
        List<Human> humans = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            try (ResultSet resultSet = st.executeQuery(sqlSelect.replace("$table_name", tableNameHuman))) {
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

    public void removeAll(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlDelete.replace("$table_name", tableName));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
