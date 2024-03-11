package mouse.project.lib.injector.sources.constructor;

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
    void satisfy() {
        Constructor<SampleClass> constructor = helper.getConstructor(SampleClass.class, "1");
        requirementProvider.getConstructor(constructor);
    }

    @Test
    void testSatisfy() {
    }

    @Test
    void isFullySatisfied() {

    }

    @Test
    void initWith() {
    }

    @Test
    void construct() {
    }
}