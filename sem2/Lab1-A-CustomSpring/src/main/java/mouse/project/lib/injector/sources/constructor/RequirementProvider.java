package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public interface RequirementProvider {
    <T> ConstructorRequirement<T> getConstructor(Constructor<T> constructor);
    FieldRequirement getField(Field field);
}
