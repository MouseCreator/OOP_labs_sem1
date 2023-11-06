package univ.lab.problem1.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelGeneratorTest {

    @Test
    void generate() {
        ModelGenerator generator = new ModelGenerator();
        Model model = generator.generate();
        assertNotNull(model);
        assertTrue(model.getId()>=0);
        assertTrue(model.getName().length()>=5);
        assertTrue(model.getDescription().split(" ").length>=3);
    }
}