package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;

public interface CardProducer<T> {
    T produce(CardContainer container);
}
