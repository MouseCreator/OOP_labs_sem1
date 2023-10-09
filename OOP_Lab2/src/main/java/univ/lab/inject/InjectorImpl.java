package univ.lab.inject;

import java.lang.reflect.Field;

public class InjectorImpl implements Injector {
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
}
