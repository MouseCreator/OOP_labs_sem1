package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.access.CardAccess;

public interface CardProducer<T> {
    T produce(CardAccess container);
    void afterConstruction(Object constructed, CardAccess container);
}
