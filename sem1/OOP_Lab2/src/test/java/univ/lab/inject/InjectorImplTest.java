package univ.lab.inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InjectorImplTest {
    private Injector injector;
    @BeforeEach
    void setUp() {
        injector = new InjectorImpl();
    }

    @Test
    void getInstanceDirect() {
        injector.addImplementation(GetIntegerInterface.class, new GetIntegerImplementation(1));
        GetIntegerInterface instance = injector.getInstance(GetIntegerInterface.class);
        assertEquals(1, instance.get());
    }
    @Test
    void getInstanceByClass() {
        injector.addImplementation(GetIntegerInterface.class, GetIntegerImplementation.class);
        GetIntegerInterface instance = injector.getInstance(GetIntegerInterface.class);
        assertEquals(20, instance.get());
    }

    @Test
    void addImplementation() {
        Class<?> prev1 = injector.addImplementation(GetIntegerInterface.class, new GetIntegerImplementation(20));
        assertNull(prev1);
        Class<?> prev2 = injector.addImplementation(GetIntegerInterface.class, SecondGetImplementation.class);
        assertEquals(GetIntegerImplementation.class, prev2);
    }
}