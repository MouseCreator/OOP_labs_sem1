package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.injector.sources.RequiredClass;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RequirementsMap {
    int size();
    Set<RequiredClass> getAll();
    Set<RequiredClass> getUnsatisfied();
    void add(RequiredClass requiredClass);
    void addAll(Collection<RequiredClass> requiredClass);
    void satisfy(RequiredClass requiredClass, Object satisfyWith);
    boolean isSatisfied();
    boolean isSatisfied(RequiredClass requiredClass);
    List<Object> getImplementations(List<RequiredClass> getFor);
    Object getImplementation(RequiredClass getFor);
}
