package mouse.project.lib.injector.sources.constructor;

import lombok.Getter;
import lombok.Setter;
import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class RequirementsMapImpl implements RequirementsMap {
    private final Map<RequiredClass, ImplementationWrapper> map;

    public RequirementsMapImpl() {
        map = new HashMap<>();
    }

    @Setter
    @Getter
    private static class ImplementationWrapper {
        private Object implementation;
        public ImplementationWrapper() {
            implementation = null;
        }
        public boolean isSatisfied() {
            return implementation != null;
        }
    }
    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Set<RequiredClass> getAll() {
        return new HashSet<>(map.keySet());
    }

    @Override
    public Set<RequiredClass> getUnsatisfied() {
        return map.keySet()
                .stream().filter(k -> !map.get(k).isSatisfied())
                .collect(Collectors.toSet());
    }

    @Override
    public void add(RequiredClass requiredClass) {
        map.put(requiredClass, new ImplementationWrapper());
    }

    @Override
    public void addAll(Collection<RequiredClass> requiredClasses) {
       for (RequiredClass requiredClass : requiredClasses) {
           add(requiredClass);
       }
    }

    @Override
    public void satisfy(RequiredClass requiredClass, Object satisfyWith) {
        ImplementationWrapper implementationWrapper = map.get(requiredClass);
        if (implementationWrapper == null) {
            throw new IOCException("Cannot satisfy not-existing requirement: " + requiredClass);
        }
        use(requiredClass, satisfyWith, implementationWrapper);
    }

    private void use(RequiredClass requiredClass, Object satisfyWith, ImplementationWrapper wrapper) {
        Class<?> clazz = requiredClass.getRequiredClass();
        if (clazz.isPrimitive()) {
            handlePrimitive(satisfyWith, clazz, wrapper);
        } else {
            handleObject(satisfyWith, requiredClass, wrapper);
        }
    }

    private void handleObject(Object satisfyWith, RequiredClass requiredClass, ImplementationWrapper wrapper) {
        Class<?> clazz = requiredClass.getRequiredClass();
        try {
            Object casted = clazz.cast(satisfyWith);
            wrapper.setImplementation(casted);
        } catch (Exception e) {
            throw new IOCException("Cannot cast to required class " +
                    clazz + " implementation of type "
                    + satisfyWith.getClass());
        }
    }

    private void handlePrimitive(Object satisfyWith, Class<?> clazz, ImplementationWrapper wrapper) {
        try {
            Object casted = Utils.validatePrimitive(clazz, satisfyWith);
            wrapper.setImplementation(casted);
        } catch (IllegalArgumentException e) {
            throw new IOCException(e);
        }
    }

    @Override
    public boolean isSatisfied() {
        return getUnsatisfied().isEmpty();
    }

    @Override
    public boolean isSatisfied(RequiredClass requiredClass) {
        ImplementationWrapper implementationWrapper = map.get(requiredClass);
        if (implementationWrapper == null) {
            throw new IOCException("Cannot test if not-existing requirement is satisfied: " + requiredClass);
        }
        return implementationWrapper.isSatisfied();
    }

    @Override
    public List<Object> getImplementations(List<RequiredClass> getFor) {
        return getFor.stream()
                .map(this::getImplementation)
                .toList();
    }

    @Override
    public Object getImplementation(RequiredClass getFor) {
        ImplementationWrapper implementationWrapper = map.get(getFor);
        if (implementationWrapper == null) {
            throw new IOCException("Cannot get implementation for " + getFor + ". No such requirement.");
        }
        if (!implementationWrapper.isSatisfied()) {
            throw new IOCException("Cannot get implementation for " + getFor + ". Requirement is not satisfied");
        }
        return implementationWrapper.getImplementation();
    }
}
