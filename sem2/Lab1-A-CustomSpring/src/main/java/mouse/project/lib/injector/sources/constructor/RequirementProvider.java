package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Constructor;

public interface RequirementProvider {
    <T> ConstructorRequirement<T> getConstructor(Constructor<T> constructor);
}
