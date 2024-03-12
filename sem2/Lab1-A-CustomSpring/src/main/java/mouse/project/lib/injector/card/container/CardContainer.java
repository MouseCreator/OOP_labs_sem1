package mouse.project.lib.injector.card.container;

import java.util.Optional;

public interface CardContainer {
    <T> boolean containsImplementation(Implementation<T> implementation);
    <T> T findImplementation(Implementation<T> implementation);
    <T> Optional<T> getImplementation(Implementation<T> implementation);
    void put(Object obj);
}
