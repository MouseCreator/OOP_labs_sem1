package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.injector.sources.RequiredClass;

import java.util.List;

public interface Requirement {
    List<RequiredClass> getRequiredClasses();
    boolean isFullySatisfied();
    void satisfy(RequiredClass requiredClass, Object satisfyWith);
}
