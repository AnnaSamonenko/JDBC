import dao.AnimalDAO;
import entities.Animal;
import helper.RandomAnimalHelper;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class ConnectionTest {

    @Test
    public void testInsertion() {
        List<Animal> expectedAnimals = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            expectedAnimals.add(RandomAnimalHelper.createRandomAnimal());

        try (AnimalDAO animalDAO = new AnimalDAO()) {
            for (Animal animal : expectedAnimals)
                animalDAO.create(animal);

            List<Animal> actualAnimals = animalDAO.getAllRecords();

            assertTrue(actualAnimals.containsAll(expectedAnimals));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



}
