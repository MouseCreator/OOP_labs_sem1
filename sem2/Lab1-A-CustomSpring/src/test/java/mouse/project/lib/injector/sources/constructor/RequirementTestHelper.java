package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Constructor;
import java.util.NoSuchElementException;

public class RequirementTestHelper {

    public <T> Constructor<T> getConstructor(Class<T> clazz, String key) {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Construct annotation = constructor.getAnnotation(Construct.class);
            if (annotation == null) {
                continue;
            }
            if(annotation.key().equals(key)) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                try {
                    Constructor<T> result = clazz.getConstructor(parameterTypes);
                    result.setAccessible(true);
                    return result;
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new NoSuchElementException("No constructor with key " + key + " for class " + clazz);
    }
}
