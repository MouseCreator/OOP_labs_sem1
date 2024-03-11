package mouse.project.lib.injector.sources;

import java.util.Collection;
import java.util.List;

public interface ClassDescriptorPool {
    void append(ClassDescriptor descriptor);
    void append(Class<?> clazz, RequiredClass requiredClass);
    void append(Class<?> clazz, Collection<RequiredClass> requiredClass);
    ClassDescriptor get(Class<?> clazz);
    List<ClassDescriptor> getAll();
}
