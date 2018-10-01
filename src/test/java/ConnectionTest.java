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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionTest {

    private static Connection connection;
    private static final String DATABASE_NAME = "animal_db";
    private static final String ANIMALS_TABLE_NAME = "home_animals";
    private static final String HUMAN_TABLE_NAME = "humans";
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String PASSWORD = "root";
    private static final String NAME = "root";

    @Before
    public void generateData() throws ClassNotFoundException, SQLException {
        connection = MySQLDatabaseConnection.getConnection(URL, NAME, PASSWORD, DATABASE_NAME);
        //fillHumans();
        //fillAnimals();
    }

    @Test
    public void test() {
        AnimalDAO animalDAO = new AnimalDAO(connection);
        HumanDAO humanDAO = new HumanDAO(connection);

        List<Animal> animals = animalDAO.getAll(ANIMALS_TABLE_NAME, HUMAN_TABLE_NAME);
        List<Human> humans = humanDAO.getAll(HUMAN_TABLE_NAME, ANIMALS_TABLE_NAME);
    }

    //    @After
    public void testRemovingAllRecords() throws SQLException {

        AnimalDAO animalDAO = new AnimalDAO(connection);
        HumanDAO humanDAO = new HumanDAO(connection);

        animalDAO.removeAll(ANIMALS_TABLE_NAME);
        List<Animal> animals = animalDAO.getAll(ANIMALS_TABLE_NAME, HUMAN_TABLE_NAME);
        //assertEquals(animals.size(), 0);

        humanDAO.removeAll(HUMAN_TABLE_NAME);
        List<Human> humans = humanDAO.getAll(HUMAN_TABLE_NAME, ANIMALS_TABLE_NAME);
        //assertEquals(humans.size(), 0);

        connection.close();
    }

    private void fillHumans() {
        List<Human> expectedHumanList = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            expectedHumanList.add(GenerateDataHelper.generateHuman());

        HumanDAO humanDAO = new HumanDAO(connection);
        for (Human human : expectedHumanList)
            humanDAO.create(HUMAN_TABLE_NAME, human);

        List<Human> actualHumanList = humanDAO.getAll(HUMAN_TABLE_NAME, ANIMALS_TABLE_NAME);
        assertTrue(actualHumanList.containsAll(expectedHumanList));
    }

    private void fillAnimals() {
        HumanDAO humanDAO = new HumanDAO(connection);
        AnimalDAO animalDAO = new AnimalDAO(connection);

        List<Animal> expectedAnimals = new ArrayList<>();
        List<Human> humans;

        humans = humanDAO.getAll(HUMAN_TABLE_NAME, ANIMALS_TABLE_NAME);

        for (int i = 0; i < 10; i++)
            expectedAnimals.add(GenerateDataHelper.generateAnimal(humans));

        for (Animal animal : expectedAnimals)
            animalDAO.create(ANIMALS_TABLE_NAME, animal);
    }
}
