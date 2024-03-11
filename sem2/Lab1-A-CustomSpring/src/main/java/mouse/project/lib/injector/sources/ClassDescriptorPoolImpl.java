package mouse.project.lib.injector.sources;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassDescriptorPoolImpl implements ClassDescriptorPool {
    
    private final Map<Class<?>, ClassDescriptor> descriptorMap;

    public ClassDescriptorPoolImpl() {
        this.descriptorMap = new HashMap<>();
    }

    @Override
    public void append(ClassDescriptor descriptor) {
        Class<?> toConstruct = descriptor.getToConstruct();
    }

    @Override
    public void append(Class<?> clazz, RequiredClass requiredClass) {

    }

    @Override
    public void append(Class<?> clazz, Collection<RequiredClass> requiredClass) {

    }

    @Override
    public ClassDescriptor get(Class<?> clazz) {
        return null;
    }

    @Override
    public List<ClassDescriptor> getAll() {
        return null;
    }
}
