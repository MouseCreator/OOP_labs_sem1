package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;

public interface ConstructorProducer<T> {
    T construct(CardContainer container);
}
