package univ.lab.inject;

import java.lang.reflect.Field;
import java.util.HashMap;

public class InjectorImpl implements Injector {
    private final HashMap<Class<?>, Class<?>> implementations = new HashMap<>();
    @Override
    public <E extends T, T> E getInstance(Class<T> interfaceClass) {
        Field[] declaredFields = interfaceClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Inject.class)) {
                //initialize field
            }
        }
        return null;
    }

    private Class<?> findImplementationTo(Class<?> interfaceClass) {
        Class<?> implementation = implementations.get(interfaceClass);
        if (implementation == null) {
            throw new IllegalStateException("No implementation for " + interfaceClass);
        }
        return implementation;
    }

    private void addImplementation(Class<?> interfaceClass, Class<?> implClass) {
        implementations.put(interfaceClass, implClass);
    }
}
