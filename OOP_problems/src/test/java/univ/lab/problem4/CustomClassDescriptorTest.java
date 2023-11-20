package univ.lab.problem4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomClassDescriptorTest {

    @Test
    void describe() {
        CustomClassDescriptor customClassDescriptor = new CustomClassDescriptor(true);
        String threadDescription = customClassDescriptor.describe(Thread.class);
        assertNotNull(threadDescription);
        System.out.println(threadDescription);
    }
}