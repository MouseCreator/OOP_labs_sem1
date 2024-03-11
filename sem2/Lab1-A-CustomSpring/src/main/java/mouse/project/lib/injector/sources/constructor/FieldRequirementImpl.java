package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;

import java.lang.reflect.Field;
import java.util.List;

public class FieldRequirementImpl implements FieldRequirement {
    private Field field;
    private final ConstructorHelper helper;
    private RequirementsSet requirementsSet;
    FieldRequirementImpl() {
        helper = ConstructorHelper.get();
        requirementsSet = null;
        field = null;
    }
    @Override
    public void initWith(Field field) {
        this.field = field;
        field.setAccessible(true);
        RequiredClass requiredClass = helper.inspectField(field);
        requirementsSet = new RequirementsSet();
        requirementsSet.add(requiredClass);
    }

    @Override
    public void inject(Object toInject) {
        if (field == null) {
            throw new IOCException("Field injector is not initialized");
        }
        Object impl = requirementsSet.validateAndGet(0);
        try {
            field.set(impl, toInject);
        } catch (IllegalAccessException e) {
            throw new IOCException(e);
        }
    }

    @Override
    public void satisfy(Object with) {
        requirementsSet.satisfy(0, with);
    }

    @Override
    public List<RequiredClass> getRequiredClasses() {
        return requirementsSet.getRequirements();
    }

    @Override
    public boolean isFullySatisfied() {
        return requirementsSet.isSatisfied();
    }
}
