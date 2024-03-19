package mouse.project.lib.injector.card.container;

import mouse.project.lib.annotation.Name;
import mouse.project.lib.annotation.Primary;
import mouse.project.lib.annotation.Prototype;

public class ImplementationCreatorImpl implements ImplementationCreator {
    @Override
    public <T> Implementation<T> getImplementation(Class<T> tClass) {
        Name annotation = tClass.getAnnotation(Name.class);
        String name = annotation == null ? null : annotation.name();
        Implementation<T> implementation = new Implementation<>(tClass, name);
        if (tClass.isAnnotationPresent(Primary.class)) {
            implementation.setPrimary(true);
        }
        if (tClass.isAnnotationPresent(Prototype.class)) {
            implementation.setPrototype(true);
        }
        return implementation;
    }
}
