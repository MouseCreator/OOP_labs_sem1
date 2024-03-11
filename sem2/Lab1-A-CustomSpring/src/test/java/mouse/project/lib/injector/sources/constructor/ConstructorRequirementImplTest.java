package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class ConstructorRequirementImplTest {

    private final static String TEXT_DEFAULT = "default";
    private final static Integer NUM_DEFAULT = 0;
    private RequirementProvider requirementProvider;
    private RequirementTestHelper helper;

    @BeforeEach
    void setUp() {
        requirementProvider = new RequirementProviderImpl();
        helper = new RequirementTestHelper();
    }

    private static class SampleClass {
        private final String text;
        private final Integer number;
        @Construct(key = "0")
        public SampleClass() {
            text = TEXT_DEFAULT;
            number = NUM_DEFAULT;
        }
        @Construct(key = "1")
        public SampleClass(String text) {
            this.text = text;
            number = NUM_DEFAULT;
        }
        @Construct(key = "2")
        public SampleClass(Integer number) {
            this.text = TEXT_DEFAULT;
            this.number = number;
        }
        @Construct(key = "3")
        public SampleClass(String text, int number) {
            this.text = text;
            this.number = number;
        }

        @Construct(key = "4")
        public SampleClass(int number) {
            this.text = TEXT_DEFAULT;
            this.number = number;
        }
    }

    @Test
    void testCreatesEmpty() {
        Constructor<SampleClass> constructor = helper.getConstructor(SampleClass.class, "0");
        ConstructorRequirement<SampleClass> cr = requirementProvider.getConstructor(constructor);
        SampleClass construct = cr.construct();
        assertEquals(NUM_DEFAULT, construct.number);
        assertEquals(TEXT_DEFAULT, construct.text);
    }

    @Test
    void testSatisfyClass() {
        Constructor<SampleClass> constructor = helper.getConstructor(SampleClass.class, "1");
        ConstructorRequirement<SampleClass> cr = requirementProvider.getConstructor(constructor);
        cr.satisfy(new RequiredClass(String.class), "Hello");
        SampleClass construct = cr.construct();
        assertEquals("Hello", construct.text);
    }

    @Test
    void testSatisfyArgument() {
        Constructor<SampleClass> constructor = helper.getConstructor(SampleClass.class, "2");
        ConstructorRequirement<SampleClass> cr = requirementProvider.getConstructor(constructor);
        cr.satisfy(0, 100);
        SampleClass construct = cr.construct();
        assertEquals(100, construct.number);
    }

    @Test
    void testSatisfyIllegal() {
        Constructor<SampleClass> stringConstructor = helper.getConstructor(SampleClass.class, "1");
        ConstructorRequirement<SampleClass> strCr = requirementProvider.getConstructor(stringConstructor);
        assertThrows(IOCException.class, () -> strCr.satisfy(new RequiredClass(String.class), 100),
                "Expected to throw if object is not a String");
        assertThrows(IOCException.class, () -> strCr.satisfy(new RequiredClass(Integer.class), 100),
                "Expected to throw if no integer argument type in constructor");
        assertThrows(IOCException.class, () -> strCr.satisfy(0, 100),
                "Expected to throw if the first argument is not a String.");
    }

    @Test
    void isFullySatisfied() {
        Constructor<SampleClass> constructor = helper.getConstructor(SampleClass.class, "3");
        ConstructorRequirement<SampleClass> cr = requirementProvider.getConstructor(constructor);
        assertFalse(cr.isFullySatisfied());
        assertThrows(IOCException.class, cr::construct);

        cr.satisfy(0, "Hello");
        assertFalse(cr.isFullySatisfied());
        assertThrows(IOCException.class, cr::construct);

        cr.satisfy(1, 1);
        assertTrue(cr.isFullySatisfied());
        SampleClass construct = cr.construct();
        assertEquals("Hello", construct.text);
        assertEquals(1, construct.number);
    }

    @Test
    void testPrimitives() {
        Constructor<SampleClass> constructor = helper.getConstructor(SampleClass.class, "4");
        ConstructorRequirement<SampleClass> cr = requirementProvider.getConstructor(constructor);
        assertThrows(IOCException.class, () -> cr.satisfy(0, 1L),
                "Expected to throw, since int != long");
        assertDoesNotThrow(() -> cr.satisfy(0, 1));
    }
}