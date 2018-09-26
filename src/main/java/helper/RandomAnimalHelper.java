package helper;

import entities.Animal;

import java.util.Random;

public class RandomAnimalHelper {

    private static Random random = new Random();

    private RandomAnimalHelper() {
    }

    public static Animal createRandomAnimal() {
        Animal animal = new Animal();
        animal.setAlias(generateAlias());
        animal.setId(random.nextInt(100));
        animal.setHasOwner(random.nextBoolean());
        return animal;
    }

    private static String generateAlias() {
        String alphabet = "abcdefhijkprstuvwx";
        StringBuilder result = new StringBuilder();
        result.append(Character.toUpperCase(alphabet.charAt(random.nextInt(alphabet.length()))));
        for (int i = 0; i < 5; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return result.toString();
    }

}
