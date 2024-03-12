package mouse.project.lib.injector.sources.producer;

import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.injector.sources.constructor.*;
import mouse.project.lib.injector.sources.scan.ClassPreroducer;

import java.util.ArrayList;
import java.util.List;

public class DynamicClassProducer<T> implements ClassProducer, ClassPreroducer<T> {
    private ConstructorRequirement<T> constructor;
    private final List<SetterRequirement> setters;
    private final List<FieldRequirement> fieldInjections;
    private RequirementsMap requirementsMap;
    public DynamicClassProducer() {
        constructor = null;
        setters = new ArrayList<>();
        fieldInjections = new ArrayList<>();
        requirementsMap = new RequirementsMapImpl();
    }

    public void setPrimaryConstructor(ConstructorRequirement<T> constructor) {
        if (isInitialized()) {
            throw new IOCException("Constructor is already defined: " + constructor);
        }
        this.constructor = constructor;
    }

    public void addSetter(SetterRequirement setterRequirement) {
        this.setters.add(setterRequirement);
    }
    public void addFieldRequirements(FieldRequirement fieldRequirement) {
        this.fieldInjections.add(fieldRequirement);
    }
    public boolean isInitialized() {
        return constructor != null;
    }
    public List<RequiredClass> getAllRequirements() {
        if (!isInitialized()) {
            throw new IOCException("Requirements call before setting primary constructor");
        }
        List<RequiredClass> requiredClasses = new ArrayList<>(constructor.getRequiredClasses());
        for (SetterRequirement sr : setters) {
            requiredClasses.addAll(sr.getRequiredClasses());
        }
        for (FieldRequirement fr : fieldInjections) {
            requiredClasses.addAll(fr.getRequiredClasses());
        }
        return requiredClasses
                .stream()
                .distinct()
                .toList();
    }

    @Override
    public Object produceClass() {
        return null;
    }

    @Override
    public List<RequiredClass> getRequiredClasses() {
        return null;
    }

    @Override
    public List<RequiredClass> getUnsatisfiedRequirements() {
        return null;
    }
}
