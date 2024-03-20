package mouse.project.lib.injector.card.container;

import mouse.project.lib.annotation.Name;
import mouse.project.lib.annotation.Primary;
import mouse.project.lib.annotation.Prototype;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class ImplementationCreatorImpl implements ImplementationCreator {
    @Override
    public <T> Implementation<T> getImplementation(Class<T> tClass) {
        return getForAnnotated(tClass, tClass);
    }

    @Override
    public Implementation<?> getImplementation(Method method) {
        Class<?> tClass = method.getReturnType();
        return getForAnnotated(method, tClass);
    }

    private <T> Implementation<T> getForAnnotated(AnnotatedElement annotated, Class<T> tClass) {
        Name annotation = annotated.getAnnotation(Name.class);
        String name = annotation == null ? null : annotation.name();
        Implementation<T> implementation = new Implementation<>(tClass, name);
        if (annotated.isAnnotationPresent(Primary.class)) {
            implementation.setPrimary(true);
        }
        if (annotated.isAnnotationPresent(Prototype.class)) {
            implementation.setPrototype(true);
        }
        return implementation;
    }
}
