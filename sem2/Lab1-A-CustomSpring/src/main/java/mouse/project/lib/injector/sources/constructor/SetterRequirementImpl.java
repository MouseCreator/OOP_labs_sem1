package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SetterRequirementImpl implements SetterRequirement {
    private Method method;
    private final ConstructorHelper helper;
    private RequirementsSet requirementsSet;

    public SetterRequirementImpl() {
        helper = ConstructorHelper.get();
        requirementsSet = null;
        method = null;
    }

    @Override
    public void satisfy(RequiredClass requiredClass, Object satisfyWith) {
        requirementsSet.satisfy(requiredClass, satisfyWith);
    }

    @Override
    public void satisfy(int argument, Object satisfyWith) {
        requirementsSet.satisfy(argument, satisfyWith);
    }

    @Override
    public List<RequiredClass> getRequiredClasses() {
        return requirementsSet.getRequirements();
    }

    @Override
    public boolean isFullySatisfied() {
        return requirementsSet.isSatisfied();
    }

    @Override
    public void initWith(Method method) {
        this.method = method;
        List<RequiredClass> requiredClasses = helper.inspectParameters(method.getParameters());
        requirementsSet = new RequirementsSet();
        requirementsSet.addAll(requiredClasses);
    }

    @Override
    public Object setInto(Object obj) {
        List<RequiredClass> unsatisfied = requirementsSet.getUnsatisfied();
        if (!unsatisfied.isEmpty()) {
            throw new IOCException("Cannot call method with unsatisfied requirements: " + unsatisfied);
        }
        int parameterCount = requirementsSet.size();
        Object[] params = new Object[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            params[i] = requirementsSet.validateAndGet(i);
        }
        try {
            method.setAccessible(true);
            return method.invoke(obj, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IOCException("Failed to create instance", e);
        }
    }
}
