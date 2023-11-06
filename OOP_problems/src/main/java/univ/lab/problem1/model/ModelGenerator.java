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
        StringBuilder builder = new StringBuilder(generateString());
        for (int i = 0; i < wordCount; i++) {
            builder.append(' ').append(generateString());
        }
        return builder.toString();
    }

    private String generateName() {
        return generateString();
    }
    private String generateString() {
        int length = random.nextInt(10) + 5;
        return generateStringOfLength(length);
    }

    private String generateStringOfLength(int length) {
        StringBuilder builder = new StringBuilder('A'+random.nextInt(26));
        for (int i = 1; i < length; i++) {
            builder.append('a'+random.nextInt(26));
        }
        return builder.toString();
    }
}
