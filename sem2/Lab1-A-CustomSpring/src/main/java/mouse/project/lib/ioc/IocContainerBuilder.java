package mouse.project.lib.ioc;

import java.lang.reflect.Method;

public interface IocContainerBuilder {
    void addClass(Class<?> clazz);
    void addFactory(Method method);
    IocContainer get();
}
