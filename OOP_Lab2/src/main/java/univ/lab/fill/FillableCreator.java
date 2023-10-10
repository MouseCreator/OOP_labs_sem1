package univ.lab.fill;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class FillableCreator {
    private HashMap<String, Class<?>> map;
    public boolean isElementDeclaration(String qName) {
        return map.containsKey(qName);
    }
    public void add(Fillable fillable) {
        map.put(fillable.name(), fillable.getClass());
    }
    public Fillable createNew(String qName) {
        Class<?> myClass = map.get(qName);
        try {
            Constructor<?> constructor = myClass.getConstructor();
            Object instance = constructor.newInstance();
            if (instance.getClass().isAnnotationPresent(Fillable.class)) {
                return (Fillable) instance;
            } else {
                throw new InstantiationException("Class does not implement @Fillable");
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
