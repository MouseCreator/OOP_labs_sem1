package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.annotation.UseNamed;
import mouse.project.lib.injector.sources.RequiredClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.List;

public class ConstructorRequirementImpl<T> implements ConstructorRequirement<T> {
    private Constructor<T> constructor;

    private ConstructorHelper helper;
    @Override
    public List<RequiredClass> getRequiredClasses() {
        return null;
    }

    @Override
    public void satisfy(RequiredClass requiredClass) {

    }

    @Override
    public boolean isFullySatisfied() {
        return false;
    }

    @Override
    public void applyAll() {

    }

    @Override
    public void initWith(Constructor<T> constructor) {
        Parameter[] parameters = constructor.getParameters();
    }

}
