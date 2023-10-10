package univ.lab.inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class InjectorImpl implements Injector {
    final HashMap<Class<?>, Class<?>> implementations = new HashMap<>();
    private final HashMap<Class<?>, Object> instances = new HashMap<>();
    @Override
    public <T> T getInstance(Class<T> interfaceClass) {
        Class<?> myClass = findImplementationTo(interfaceClass);
        Field[] declaredFields = myClass.getDeclaredFields();
        Object classImplementation = createInstance(myClass);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object instance = getInstance(field.getType());
                setField(field, classImplementation, instance);
            }
        }

        if (classImplementation == null) {
            classImplementation = createInstance(myClass);
        }
        return interfaceClass.cast(classImplementation);
    }

    private Object createInstance(Class<?> myClass) {
        if(instances.containsKey(myClass)) {
            return instances.get(myClass);
        }
        try {
            Constructor<?> constructor = myClass.getConstructor();
            Object instance = constructor.newInstance();
            instances.put(myClass, instance);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Unable to create instance of " + myClass.getName());
        }
    }

    private Class<?> findImplementationTo(Class<?> interfaceClass) {
        if (!interfaceClass.isInterface()) {
            return interfaceClass;
        }
        Class<?> implementation = implementations.get(interfaceClass);
        if (implementation == null) {
            throw new IllegalStateException("No implementation for " + interfaceClass.getName());
        }
        return implementation;
    }

    public Class<?> addImplementation(Class<?> interfaceClass, Class<?> implClass) {
        return implementations.put(interfaceClass, implClass);
    }

    private <T> void setField(Field field, Object obj, T value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
