import dao.AnimalDAO;
import dao.HumanDAO;
import entities.Animal;
import entities.Human;
import helper.GenerateDataHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.MySQLDatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionTest {
    private static final String DATABASE_NAME = "animal_db";
    private static final String ANIMALS_TABLE_NAME = "home_animals";
    private static final String HUMAN_TABLE_NAME = "humans";
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String PASSWORD = "root";
    private static final String NAME = "root";

    @Before
    public void generateData() throws ClassNotFoundException, SQLException {
//        fillHumans();
//        fillAnimals();
    }

    @Test
    public void testRelationship() {

    }

    private void fillHumans() throws ClassNotFoundException, SQLException {
        Connection connection = MySQLDatabaseConnection.getConnection(URL, NAME, PASSWORD, DATABASE_NAME);
        List<Human> expectedHumanList = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            expectedHumanList.add(GenerateDataHelper.generateHuman());

        try (HumanDAO humanDAO = new HumanDAO(connection)) {
            for (Human human : expectedHumanList)
                humanDAO.create(HUMAN_TABLE_NAME, human);

            List<Human> actualHumanList = humanDAO.getAllHumans(HUMAN_TABLE_NAME);
            assertTrue(actualHumanList.containsAll(expectedHumanList));
        }
    }

    private void fillAnimals() throws ClassNotFoundException, SQLException {
        Connection connection = MySQLDatabaseConnection.getConnection(URL, NAME, PASSWORD, DATABASE_NAME);

        List<Animal> expectedAnimals = new ArrayList<>();
        List<Human> humans;

        try (HumanDAO humanDAO = new HumanDAO(connection)) {
            humans = humanDAO.getAllHumans(HUMAN_TABLE_NAME);
        }

        connection = MySQLDatabaseConnection.getConnection(URL, NAME, PASSWORD, DATABASE_NAME);
        List<Integer> humansId = humans.stream().map(human -> human.getId()).collect(Collectors.toList());

        for (int i = 0; i < 10; i++)
            expectedAnimals.add(GenerateDataHelper.generateAnimal(humansId));

        try (AnimalDAO animalDAO = new AnimalDAO(connection)) {
            for (Animal animal : expectedAnimals)
                animalDAO.create(ANIMALS_TABLE_NAME, animal);
        }
    }

    @After
    public void testRemovingAllRecords() throws SQLException, ClassNotFoundException {
        Connection connection = MySQLDatabaseConnection.getConnection(URL, NAME, PASSWORD, DATABASE_NAME);

        try (AnimalDAO animalDAO = new AnimalDAO(connection)) {
            animalDAO.removeAllRecords(ANIMALS_TABLE_NAME);
            List<Animal> animals = animalDAO.getAllRecords(ANIMALS_TABLE_NAME);
            assertEquals(animals.size(), 0);
        }

        connection = MySQLDatabaseConnection.getConnection(URL, NAME, PASSWORD, DATABASE_NAME);
        try (HumanDAO humanDAO = new HumanDAO(connection)) {
            humanDAO.removeAllRecords(HUMAN_TABLE_NAME);
            List<Human> humans = humanDAO.getAllHumans(ANIMALS_TABLE_NAME);
            assertEquals(humans.size(), 0);
        }
    }
}
