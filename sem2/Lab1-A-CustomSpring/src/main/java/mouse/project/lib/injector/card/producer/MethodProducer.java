package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;

public interface MethodProducer<T> {
    T call(CardContainer container);
}
