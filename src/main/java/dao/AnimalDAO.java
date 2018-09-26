package dao;

import entities.Animal;
import helper.RandomAnimalHelper;
import utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO implements AutoCloseable {

    private DatabaseConnection connection;

    public AnimalDAO() throws SQLException {
        connection = DatabaseConnection.getInstance();
    }

    public List<Animal> getAllRecords() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * from home_animals";
        try (Statement statement = connection.getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Animal animal = new Animal();
                    animal.setId(resultSet.getInt("animal_id"));
                    animal.setAlias(resultSet.getString("alias"));
                    animal.setHasOwner(resultSet.getBoolean("has_owner"));
                    animals.add(animal);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return animals;
    }

    public void create(Animal animal) {
        try (PreparedStatement st = connection.getConnection().prepareStatement("INSERT INTO home_animals(animal_id, alias, has_owner) VALUES(?, ?, ?)")) {
            st.setInt(1, animal.getId());
            st.setString(2, animal.getAlias());
            if (animal.getHasOwner())
                st.setInt(3, 1);
            else
                st.setInt(3, 0);
            st.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
