package mouse.project.lib.injector.card.scan;

import mouse.project.lib.injector.card.definition.DefinedCard;

public interface CardScanner {
    <T> DefinedCard<T> scan(Class<T> tClass);
}
