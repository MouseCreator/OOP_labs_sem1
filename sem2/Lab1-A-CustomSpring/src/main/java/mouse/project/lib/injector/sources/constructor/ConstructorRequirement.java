package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Constructor;

public interface ConstructorRequirement<T> extends Requirement {
    void initWith(Constructor<T> constructor);
    T construct();
}
