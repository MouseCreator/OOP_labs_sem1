package mouse.project.lib.injector.card.scan;

import mouse.project.lib.injector.card.definition.CardDefinition;

public interface CardScanner {
    <T> CardDefinition<T> scan(Class<T> tClass);
}
