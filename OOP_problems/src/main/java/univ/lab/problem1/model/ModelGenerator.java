package univ.lab.problem1.model;

import java.util.Random;

public class ModelGenerator {
    private final Random random = new Random();
    public Model generate() {
        int id = random.nextInt(999)+1;
        String name = generateName();
        String description = generateDescription();
        return new Model(id, name, description);
    }

    private String generateDescription() {
        int wordCount = random.nextInt(10) + 3;
        String initial = capitalize(generateString());
        StringBuilder builder = new StringBuilder(initial);
        for (int i = 1; i < wordCount; i++) {
            builder.append(' ').append(generateString());
        }
        return builder.toString();
    }

    private String generateName() {
        return capitalize(generateString());
    }
    private String generateString() {
        int length = random.nextInt(10) + 5;
        return generateStringOfLength(length);
    }
    private String capitalize(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        char firstChar = Character.toUpperCase(string.charAt(0));
        return firstChar + string.substring(1);
    }
    private String generateStringOfLength(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append((char) ('a' + random.nextInt(26)));
        }
        return builder.toString();
    }
}
