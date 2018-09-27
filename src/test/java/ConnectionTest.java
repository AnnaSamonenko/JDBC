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

    String databaseName = "animal_db";
    String url = "jdbc:mysql://localhost:3306";

    @Test
    public void testConnection() {
        Connection connection = MySQLDatabaseConnection.getConnection(url, "root", "root", databaseName);
        assertNotNull(connection);
    }

    @Test
    public void testInsertion() {
        Connection connection = MySQLDatabaseConnection.getConnection(url, "root", "root", databaseName);

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
        Connection connection = MySQLDatabaseConnection.getConnection(url, "root", "root", databaseName);

        try (AnimalDAO animalDAO = new AnimalDAO(connection)) {
            animalDAO.removeAllRecords();
            List<Animal> animals = animalDAO.getAllRecords();
            assertEquals(animals.size(), 0);
        }
    }
}
