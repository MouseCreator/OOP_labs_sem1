package mouse.project.lib.injector.sources.producer;

import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.injector.sources.constructor.FactoryMethodRequirement;
import mouse.project.lib.injector.sources.constructor.RequirementsMap;

import java.lang.reflect.Method;
import java.util.List;

public class FactoryMethodProducer<T> implements ClassProducer<T> {

    private FactoryMethodRequirement<T> factoryMethodRequirement;
    private final RequirementsMap requirementsMap;

    public FactoryMethodProducer(RequirementsMap requirementsMap) {
        this.requirementsMap = requirementsMap;
    }

    @Override
    public T produceClass() {
        return factoryMethodRequirement.create();
    }

    @Override
    public void satisfy(RequiredClass requiredClass, Object with) {
        factoryMethodRequirement.satisfy(requiredClass, with);
    }

    @Override
    public List<RequiredClass> getRequiredClasses() {
        return factoryMethodRequirement.getRequiredClasses();
    }

    @Override
    public List<RequiredClass> getUnsatisfiedRequirements() {
        return null;
    }

    public void fromMethod(Method method) {

    }
}
