package mouse.project.lib.injector.card.access;

import mouse.project.lib.injector.card.container.Implementation;

import java.util.Collection;

public interface CardAccess {
    <T> T getImplementation(Implementation<T> implementation);
    <T> Collection<T> getAllImplementations(Implementation<T> implementation);
}
