package mouse.project.lib.injector.card.container;

import java.lang.reflect.Method;

public interface ImplementationCreator {
    <T> Implementation<T> getImplementation(Class<T> tClass);
    Implementation<?> getImplementation(Method method);
}
