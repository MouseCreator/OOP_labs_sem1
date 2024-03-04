package univ.lab.problem4;

import org.junit.jupiter.api.Test;
import univ.lab.problem4.model.Bird;

import static org.junit.jupiter.api.Assertions.*;

class CustomClassDescriptorTest {

    @Test
    void describe() {
        CustomClassDescriptor customClassDescriptor = new CustomClassDescriptor(true);
        String birdDescription = customClassDescriptor.describe(Bird.class);
        assertNotNull(birdDescription);
        assertNotNull(birdDescription);
        assertTrue(birdDescription.contains("public final native void java.lang.Object.notify()"));
        assertTrue(birdDescription.contains("private int univ.lab.problem4.model.Bird.wingLength"));
        assertTrue(birdDescription.contains("public final native void java.lang.Object.notify()"));
        assertTrue(birdDescription.contains("Parent class: univ.lab.problem4.model.Animal"));
        assertTrue(birdDescription.contains("Parent class: java.lang.Object"));
        assertTrue(birdDescription.contains("public univ.lab.problem4.model.Bird(java.lang.String,int)"));
    }
}