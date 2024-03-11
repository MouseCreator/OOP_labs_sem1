package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.injector.sources.annotation.GetMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class SetterRequirementImplTest {

    private RequirementProvider requirementProvider;
    private RequirementTestHelper helper;

    private final static String DEFAULT_TEXT = "DEF";

    @BeforeEach
    void setUp() {
        requirementProvider = new RequirementProviderImpl();
        helper = new RequirementTestHelper();
    }

    private static class SampleClass {
        private String text;

        public SampleClass() {
            this.text = DEFAULT_TEXT;
        }
        @GetMethod(key = "text")
        public String setText(String newText) {
            String prev = this.text;
            this.text = newText;
            return prev;
        }

    }

    @Test
    void testSatisfyByArgument() {
        Method method = helper.getMethod(SampleClass.class, "text");
        SetterRequirement setter = requirementProvider.getSetter(method);
        setter.satisfy(0, "New");
        SampleClass sampleClass = new SampleClass();
        Object prev = setter.setInto(sampleClass);
        assertEquals(DEFAULT_TEXT, prev);
        assertEquals("New", sampleClass.text);
    }

    @Test
    void testSatisfyByClass() {
        Method method = helper.getMethod(SampleClass.class, "text");
        SetterRequirement setter = requirementProvider.getSetter(method);
        setter.satisfy(new RequiredClass(String.class), "New");
        SampleClass sampleClass = new SampleClass();
        Object prev = setter.setInto(sampleClass);
        assertEquals(DEFAULT_TEXT, prev);
        assertEquals("New", sampleClass.text);
    }

    @Test
    void isFullySatisfied() {
        Method method = helper.getMethod(SampleClass.class, "text");
        SetterRequirement setter = requirementProvider.getSetter(method);
        assertFalse(setter.isFullySatisfied());
        setter.satisfy(new RequiredClass(String.class), "New");
        assertTrue(setter.isFullySatisfied());
    }
}