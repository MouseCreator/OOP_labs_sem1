package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;

public interface SetterProducer {
    void apply(Object applyTo, CardContainer container);
}
