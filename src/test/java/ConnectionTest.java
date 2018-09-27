import dao.AnimalDAO;
import entities.Animal;
import helper.RandomAnimalHelper;
import org.junit.Test;
import utils.MySQLDatabaseConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ConnectionTest {

    private static final String DATABASE_NAME = "animal_db";
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String PASSWORD = "root";
    private static final String NAME = "root";

    @Test
    public void testConnection() {
        Connection connection = MySQLDatabaseConnection.getConnection(URL, NAME, PASSWORD, DATABASE_NAME);
        assertNotNull(connection);
    }

    @Test
    public void testInsertion() {
        Connection connection = MySQLDatabaseConnection.getConnection(URL, NAME, PASSWORD, DATABASE_NAME);

        List<Animal> expectedAnimals = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            expectedAnimals.add(RandomAnimalHelper.createRandomAnimal());

        try (AnimalDAO animalDAO = new AnimalDAO(connection)) {
            for (Animal animal : expectedAnimals)
                animalDAO.create(animal);

            List<Animal> actualAnimals = animalDAO.getAllRecords();

            assertTrue(actualAnimals.containsAll(expectedAnimals));
        }
    }

    @Test
    public void testRemovingAllRecords() {
        Connection connection = MySQLDatabaseConnection.getConnection(URL, NAME, PASSWORD, DATABASE_NAME);

        try (AnimalDAO animalDAO = new AnimalDAO(connection)) {
            animalDAO.removeAllRecords();
            List<Animal> animals = animalDAO.getAllRecords();
            assertEquals(animals.size(), 0);
        }
    }
}
