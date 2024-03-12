package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.injector.sources.RequiredClass;

import java.util.Collection;
import java.util.List;


public interface RequirementsSet {
    int size();
    Object validateAndGet(int argument);
    void add(RequiredClass requiredClass);
    void addAll(Collection<RequiredClass> requiredClasses);
    List<RequiredClass> getRequirements();
    void satisfy(RequiredClass requiredClass, Object satisfyWith);
    void satisfy(int argument, Object satisfyWith);
    boolean isSatisfied();
    List<RequiredClass> getUnsatisfied();
}
