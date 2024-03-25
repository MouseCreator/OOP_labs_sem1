package mouse.project.lib.ioc.injector.map;

import mouse.project.lib.ioc.injector.card.container.Implementation;

import java.util.Collection;

public interface DefinedMap<E extends TypeHolder<?>> {
    E lookup(Implementation<?> implementation);
    void add(E definition);
    Collection<E> lookupAll(Implementation<?> implementation);
    <T> boolean contains(Implementation<T> implementation);
}
