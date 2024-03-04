package univ.lab.inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InjectorInitializerTest {
    private InjectorInitializer injectorInitializer;
    @BeforeEach
    void setUp() {
        injectorInitializer = new InjectorInitializer();
    }

    @Test
    void initialize_Ok() {
        Injector injector = injectorInitializer.initialize("src/test/resources/test-injector.xml");
        Class<?> previous = injector.addImplementation(GetIntegerInterface.class, SecondGetImplementation.class);
        assertNotNull(previous);
        assertEquals(previous, GetIntegerImplementation.class);
    }
    @Test
    void initialize_NoFile() {
        assertThrows(RuntimeException.class, ()->injectorInitializer.initialize("src/test/resources/not-a-file.xml"));
    }
}