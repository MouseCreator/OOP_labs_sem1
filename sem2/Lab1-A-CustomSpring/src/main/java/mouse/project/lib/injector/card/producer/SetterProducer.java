package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;

public interface SetterProducer<T> {
    void apply(T applyTo, CardContainer container);
}
