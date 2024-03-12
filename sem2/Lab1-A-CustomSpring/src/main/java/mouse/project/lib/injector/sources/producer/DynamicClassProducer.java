package mouse.project.lib.injector.sources.producer;

import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.injector.sources.constructor.*;
import mouse.project.lib.injector.sources.scan.ClassPreroducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DynamicClassProducer<T> implements ClassProducer<T>, ClassPreroducer<T> {
    private ConstructorRequirement<T> constructor;
    private final List<SetterRequirement> setters;
    private final List<FieldRequirement> fieldInjections;
    private final RequirementsMap requirementsMap;
    public DynamicClassProducer() {
        constructor = null;
        setters = new ArrayList<>();
        fieldInjections = new ArrayList<>();
        requirementsMap = new RequirementsMapImpl();
    }

    public void satisfy(RequiredClass requiredClass, Object with) {
        requirementsMap.satisfy(requiredClass, with);
    }

    public void setPrimaryConstructor(ConstructorRequirement<T> constructor) {
        if (isInitialized()) {
            throw new IOCException("Constructor is already defined: " + constructor);
        }
        this.constructor = constructor;
        addToRequirements(constructor);
    }

    private void addToRequirements(Requirement requirement) {
        requirementsMap.addAll(requirement.getRequiredClasses());
    }

    public void addSetter(SetterRequirement setterRequirement) {
        addToRequirements(setterRequirement);
        this.setters.add(setterRequirement);
    }
    public void addFieldRequirements(FieldRequirement fieldRequirement) {
        addToRequirements(fieldRequirement);
        this.fieldInjections.add(fieldRequirement);
    }
    public boolean isInitialized() {
        return constructor != null;
    }

    @Override
    public T produceClass() {
        Set<RequiredClass> unsatisfied = requirementsMap.getUnsatisfied();
        if (!unsatisfied.isEmpty()) {
            throw new IOCException("Cannot produce a class with unsatisfied requirements: " + unsatisfied);
        }
        T obj = buildObject();
        injectMethods(obj);
        injectFields(obj);
        return obj;
    }

    private void injectFields(T obj) {
        for (FieldRequirement fieldRequirement : fieldInjections) {
            satisfyAllRequirements(fieldRequirement);
            fieldRequirement.injectInto(obj);
        }
    }

    private void satisfyAllRequirements(Requirement requirement) {
        List<RequiredClass> requiredClasses = requirement.getRequiredClasses();
        for (RequiredClass requiredClass : requiredClasses) {
            requirement.satisfy(requiredClass, requirementsMap.getImplementation(requiredClass));
        }
    }

    private void injectMethods(T obj) {
        for (SetterRequirement setter : setters) {
            satisfyAllRequirements(setter);
            setter.setInto(obj);
        }
    }

    private T buildObject() {
        satisfyAllRequirements(constructor);
        return constructor.construct();
    }

    @Override
    public List<RequiredClass> getRequiredClasses() {
        return new ArrayList<>(requirementsMap.getAll());
    }

    @Override
    public List<RequiredClass> getUnsatisfiedRequirements() {
        return new ArrayList<>(requirementsMap.getUnsatisfied());
    }
}
