package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.exception.IOCException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class FieldRequirementImplTest {

    private RequirementProvider requirementProvider;
    private RequirementTestHelper helper;
    private static class SampleClass {
        private String f1;

        public SampleClass() {
            f1 = "";
        }
    }
    @BeforeEach
    void setUp() {
        requirementProvider = new RequirementProviderImpl();
        helper = new RequirementTestHelper();
    }
    @Test
    void testInject() {
        Field field = helper.getField(SampleClass.class, "f1");
        FieldRequirement fr = requirementProvider.getField(field);
        fr.satisfy("Value");
        SampleClass sampleClass = new SampleClass();
        fr.injectInto(sampleClass);
        assertEquals("Value", sampleClass.f1);
    }

    @Test
    void testIllegal() {
        Field field = helper.getField(SampleClass.class, "f1");
        FieldRequirement fr = requirementProvider.getField(field);
        assertThrows(IOCException.class, () -> fr.satisfy(100), "Cannot set Integer, where String needed");
        SampleClass sampleClass = new SampleClass();
        assertThrows(IOCException.class, () -> fr.injectInto(sampleClass), "Cannot use, when not satisfied");
        fr.satisfy("Value");
        assertThrows(IOCException.class, () -> fr.injectInto("Other Class"));
    }

    @Test
    void testSatisfied() {
        Field field = helper.getField(SampleClass.class, "f1");
        FieldRequirement fr = requirementProvider.getField(field);
        assertFalse(fr.isFullySatisfied());
        fr.satisfy("Value");
        assertTrue(fr.isFullySatisfied());
        SampleClass sampleClass = new SampleClass();
        fr.injectInto(sampleClass);
        assertTrue(fr.isFullySatisfied());
    }
}