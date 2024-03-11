package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Constructor;

public interface ConstructorRequirement<T> extends MultiRequirement {
    void initWith(Constructor<T> constructor);
    T construct();
}
