package helper;

import entities.Animal;
import entities.Human;

import java.util.List;
import java.util.Random;

public class GenerateDataHelper {

    private static Random random = new Random();

    private GenerateDataHelper() {
    }

    public static Animal generateAnimal(List<Human> possibleOwner) {
        Animal animal = new Animal();
        animal.setAlias(getRandomAnimalAlias());
        animal.setId(random.nextInt(100));
        animal.setHuman(possibleOwner.get(random.nextInt(possibleOwner.size())));
        return animal;
    }

    public static Human generateHuman() {
        Human human = new Human();
        human.setName(getRandomHumanName());
        human.setSurname(getRandomHumanSurname());
        return human;
    }

    private static String getRandomAnimalAlias() {
        String[] names = {"Daisy", "Chloe", "Lucy", "Max", "Callie", "Simba", "Leo"};
        return names[random.nextInt(names.length)];
    }

    private static String getRandomHumanName() {
        String[] names = {"Oliver", "Jake", "Amelia", "Margaret", "Elizabeth", "Charlie", "Joanne"};
        return names[random.nextInt(names.length)];
    }

    private static String getRandomHumanSurname() {
        String[] names = {"Murphy", "Smith", "Williams", "Taylor", "Jones", "Davies", "Walker"};
        return names[random.nextInt(names.length)];
    }
}
