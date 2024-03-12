package mouse.project.lib.injector.sources.constructor;

import lombok.Getter;
import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ReqSet implements RequirementsSet {
    private final List<RequirementWrapper> requirementWrapperList;
    private int argumentCount;
    public ReqSet() {
        this.requirementWrapperList = new ArrayList<>();
        argumentCount = 0;
    }

    public int size() {
        return argumentCount;
    }

    public Object validateAndGet(int argument) {
        Object impl = getByOrder(argument).impl;
        if (impl == null) {
            throw new IOCException("Argument " + argument + " has no implementation");
        }
        return impl;
    }

    private static class RequirementWrapper {
        private Object impl;
        @Getter
        private final RequiredClass requiredClass;
        @Getter
        private final int order;
        public RequirementWrapper(RequiredClass requiredClass, int order) {
            this.requiredClass = requiredClass;
            this.impl = null;
            this.order = order;
        }

        public void use(Object satisfyWith) {
            Class<?> clazz = requiredClass.getRequiredClass();
            if (clazz.isPrimitive()) {
                handlePrimitive(satisfyWith, clazz);
            } else {
                handleObject(satisfyWith, clazz);
            }
        }

        private void handleObject(Object satisfyWith, Class<?> clazz) {
            try {
                clazz.cast(satisfyWith);
                impl = satisfyWith;
            } catch (Exception e) {
                throw new IOCException("Cannot cast to required class " +
                        clazz + " implementation of type "
                        + satisfyWith.getClass());
            }
        }

        private void handlePrimitive(Object satisfyWith, Class<?> clazz) {
            try {
                impl = Utils.validatePrimitive(clazz, satisfyWith);
            } catch (IllegalArgumentException e) {
                throw new IOCException(e);
            }
        }

        public boolean isSatisfied() {
            return impl != null;
        }
    }

    public void add(RequiredClass requiredClass) {
        RequirementWrapper wrapper = new RequirementWrapper(requiredClass, argumentCount);
        argumentCount++;
        requirementWrapperList.add(wrapper);

    }
    public void addAll(Collection<RequiredClass> requiredClasses) {
        for (RequiredClass requiredClass : requiredClasses) {
            add(requiredClass);
        }
    }

    public List<RequiredClass> getRequirements() {
        return requirementWrapperList.stream()
                .map(t -> t.requiredClass)
                .toList();
    }

    public void satisfy(RequiredClass requiredClass, Object satisfyWith) {
        List<RequirementWrapper> list = requirementWrapperList.stream()
                .filter(t -> requiredClass.equals(t.requiredClass))
                .toList();
        if (list.isEmpty()) {
            throw new IOCException("Error satisfying requirement that does not exists: " + requiredClass);
        }
        list.forEach(t -> t.use(satisfyWith));
    }

    public void satisfy(int argument, Object satisfyWith) {
        RequirementWrapper byOrder = getByOrder(argument);
        byOrder.use(satisfyWith);
    }

    private RequirementWrapper getByOrder(int order) {
        List<RequirementWrapper> list = requirementWrapperList.stream()
                .filter(t -> Objects.equals(t.order, order))
                .toList();
        if (list.size() != 1) {
            throw new IOCException("Found " + list.size() + " requirements with order " + order);
        }
        return list.get(0);
    }

    public boolean isSatisfied() {
        return getUnsatisfied().isEmpty();
    }

    public List<RequiredClass> getUnsatisfied() {
        return requirementWrapperList.stream()
                .filter(r -> !r.isSatisfied())
                .map(r -> r.requiredClass)
                .toList();
    }
}
