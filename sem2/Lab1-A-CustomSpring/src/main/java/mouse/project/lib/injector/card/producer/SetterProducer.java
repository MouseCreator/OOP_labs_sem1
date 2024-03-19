package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardAccess;

public interface SetterProducer {
    void apply(Object applyTo, CardAccess container);
}
