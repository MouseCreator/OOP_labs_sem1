package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class FactoryMethodRequirementImpl<T> implements FactoryMethodRequirement<T> {
    private Object object;
    private Method method;
    private final ConstructorHelper helper;
    private RequirementsSet requirementsSet;
    private Class<T> clazz = null;
    public FactoryMethodRequirementImpl() {
        helper = ConstructorHelper.get();
        method = null;
        requirementsSet = null;
        object = null;
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
    public void satisfy(RequiredClass requiredClass, Object satisfyWith) {
        requirementsSet.satisfy(requiredClass, satisfyWith);
    }

    @Override
    public void initWith(Object object, Method method, Class<T> provides) {
        this.method = method;
        this.clazz = provides;
        this.object = object;
        List<RequiredClass> requiredClasses = helper.inspectParameters(method.getParameters());
        requirementsSet = new ReqSet();
        requirementsSet.addAll(requiredClasses);
    }

    @Override
    public T create() {
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
            return clazz.cast(method.invoke(object, params));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IOCException("Failed to create instance", e);
        }
    }
}
