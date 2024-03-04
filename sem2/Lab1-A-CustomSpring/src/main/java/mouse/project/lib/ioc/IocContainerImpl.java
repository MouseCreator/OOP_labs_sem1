package mouse.project.lib.ioc;

import mouse.project.lib.annotation.Name;
import mouse.project.lib.annotation.Primary;
import mouse.project.lib.exception.IOCException;

import java.util.*;
import java.util.stream.Collectors;

public class IocContainerImpl implements IocContainer {

    private final Map<Class<?>, List<Object>> implementations;

    public IocContainerImpl() {
        implementations = new HashMap<>();
    }

    @Override
    public <T> T getSingle(Class<? extends T> targetClass) {
        List<Object> objects = getImplementationList(targetClass);
        if (objects.size() == 1) {
            return targetClass.cast(objects.get(0));
        }
        Optional<Object> primaryImplementation = choosePrimary(objects, targetClass);
        Object obj = primaryImplementation.orElseThrow(()->
                new IOCException("Multiple implementations are provided for " + targetClass
                        + ". Cannot choose between: "+ objects));
        return targetClass.cast(obj);
    }

    private Optional<Object> choosePrimary(List<Object> objects, Class<?> target) {
        List<Object> primaries = new ArrayList<>();
        for (Object obj : objects) {
            Primary annotation = obj.getClass().getAnnotation(Primary.class);
            if (annotation == null) {
                continue;
            }
            primaries.add(obj);
        }
        if (primaries.size()==1) {
            return Optional.of(primaries.get(0));
        }
        if (primaries.size()==0) {
            return Optional.empty();
        }
        throw new IOCException("Multiple primary implementations found for class " + target.getName()
        + ". No primary. Cannot choose between: " + primaries);
    }

    @Override
    public <T> T getNamed(Class<? extends T> targetClass, String name) {
        List<Object> objects = getImplementationList(targetClass);
        List<Object> namedObjects = new ArrayList<>();
        for (Object obj : objects) {
            if (obj.getClass().isAnnotationPresent(Name.class)) {
                Name nameAnnotation = obj.getClass().getAnnotation(Name.class);
                String nameValue = nameAnnotation.name();
                if (nameValue.equals(name)) {
                    namedObjects.add(obj);
                }
            }
        }

        if (namedObjects.size() == 1) {
            return targetClass.cast(namedObjects.get(0));
        }
        Optional<Object> primaryImplementation = choosePrimary(objects, targetClass);
        Object obj = primaryImplementation.orElseThrow(()->
                new IOCException("Multiple named as " + name + " implementations are provided for "
                        + targetClass
                        + ". No primary. Cannot choose between: "+ namedObjects));

        return targetClass.cast(obj);
    }

    private List<Object> getImplementationList(Class<?> target) {
        List<Object> objects = implementations.get(target);
        if (objects == null) {
            throw new IOCException("No implementation provided for " + target.getName());
        }
        return objects;
    }

    @Override
    public <T> List<T> getList(Class<T> targetClass) {
        List<Object> objects = getImplementationList(targetClass);
        return objects.stream()
                .map(targetClass::cast).toList();
    }

    @Override
    public <T> Set<T> getSet(Class<T> targetClass) {
        List<Object> objects = getImplementationList(targetClass);
        return objects.stream()
                .map(targetClass::cast).collect(Collectors.toSet());
    }

    public void addImplementation(Object implementation) {
        addFromSuperClass(implementation);
        addFromInterfaces(implementation);
    }

    private void addFromInterfaces(Object implementation) {
        Class<?> baseClass = implementation.getClass();
        addInterfaces(baseClass, implementation);
    }
    private void addFromSuperClass(Object implementation) {
        Class<?> clazz = implementation.getClass();
        while (clazz != null) {
            addToMap(clazz, implementation);
            clazz = clazz.getSuperclass();
        }
    }
    private void addInterfaces(Class<?> clazz, Object implementation) {
        if (clazz == null) return;

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            addInterfaces(superclass, implementation);
        }

        for (Class<?> interfaceClass : clazz.getInterfaces()) {
            addToMap(interfaceClass, implementation);
            addInterfaces(interfaceClass, implementation);
        }
    }

    private void addToMap(Class<?> interfaceSample, Object implementation) {
        List<Object> objects = implementations.computeIfAbsent(interfaceSample, k -> new ArrayList<>());
        objects.add(implementation);
    }
}
