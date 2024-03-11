package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface RequirementProvider {
    <T> ConstructorRequirement<T> getConstructor(Constructor<T> constructor);
    FieldRequirement getField(Field field);
    SetterRequirement getSetter(Method method);
}
