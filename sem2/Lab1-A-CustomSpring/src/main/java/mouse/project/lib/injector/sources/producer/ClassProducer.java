package mouse.project.lib.injector.sources.producer;

import mouse.project.lib.exception.IOCException;
import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.injector.sources.constructor.ConstructorRequirement;
import mouse.project.lib.injector.sources.constructor.FieldRequirement;
import mouse.project.lib.injector.sources.constructor.SetterRequirement;

import java.util.ArrayList;
import java.util.List;

public class ClassProducer<T> {
    private ConstructorRequirement<T> constructor;
    private final List<SetterRequirement> setters;
    private final List<FieldRequirement> fieldInjections;

    public ClassProducer() {
        constructor = null;
        setters = new ArrayList<>();
        fieldInjections = new ArrayList<>();
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
}
