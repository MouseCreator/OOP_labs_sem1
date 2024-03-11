package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.injector.sources.RequiredClass;

import java.lang.reflect.Field;
import java.util.List;

public class FieldRequirementImpl implements FieldRequirement {

    private Field field;
    private final ConstructorHelper helper;
    private RequirementsSet requirementsSet;
    private FieldRequirementImpl() {
        helper = ConstructorHelper.get();
        requirementsSet = null;
    }
    @Override
    public void initWith(Field field) {
        this.field = field;
        field.setAccessible(true);
        RequiredClass requiredClass = helper.inspectField(field);
    }

    @Override
    public void inject(Object toInject) {

    }

    @Override
    public List<RequiredClass> getRequiredClasses() {
        return null;
    }

    @Override
    public void satisfy(RequiredClass requiredClass, Object satisfyWith) {

    }

    @Override
    public void satisfy(int argument, Object satisfyWith) {

    }

    @Override
    public boolean isFullySatisfied() {
        return false;
    }
}
