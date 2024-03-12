package mouse.project.lib.injector.sources.constructor;


import java.lang.reflect.Method;

public interface FactoryMethodRequirement<T> extends MultiRequirement {
    void initWith(Object object, Method method, Class<T> provides);
    T create();
}
