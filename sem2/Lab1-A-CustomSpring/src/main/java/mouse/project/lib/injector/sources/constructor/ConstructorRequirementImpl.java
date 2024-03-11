package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;

public class ConstructorRequirementImpl<T> implements ConstructorRequirement<T> {
    private Constructor<T> constructor;
    private final ConstructorHelper helper;
    private RequirementsSet requirementsSet = null;
    @Override
    public List<RequiredClass> getRequiredClasses() {
        return null;
    }
    private ConstructorRequirementImpl() {
        constructor = null;
        helper = ConstructorHelper.get();
    }

    static <T> ConstructorRequirementImpl<T> get(Constructor<T> constructor) {
        ConstructorRequirementImpl<T> instance = new ConstructorRequirementImpl<>();
        instance.initWith(constructor);
        return instance;
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
    public boolean isFullySatisfied() {
        if (requirementsSet == null) {
            throw new IllegalStateException("Constructor Requirements is not initialized");
        }
        return requirementsSet.isSatisfied();
    }

    @Override
    public void initWith(Constructor<T> constructor) {
        if (this.constructor != null) {
            throw new IOCException("Constructor requirement is already initialized");
        }
        Parameter[] parameters = constructor.getParameters();
        List<RequiredClass> requiredClasses = helper.inspectParameters(parameters);
        requirementsSet = new RequirementsSet();
        requirementsSet.addAll(requiredClasses);
        this.constructor = constructor;
    }

    @Override
    public T construct() {
        List<RequiredClass> unsatisfied = requirementsSet.getUnsatisfied();
        if (!unsatisfied.isEmpty()) {
            throw new IOCException("Cannot construct class with unsatisfied requirements: " + unsatisfied);
        }
        int parameterCount = requirementsSet.size();
        Object[] params = new Object[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            params[i] = requirementsSet.validateAndGet(i);
        }
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IOCException("Failed to create instance", e);
        }
    }

}
