package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardAccess;

public interface CardProducer<T> {
    T produce(CardAccess container);
}
