package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.access.CardAccess;

public interface SetterProducer {
    void apply(Object applyTo, CardAccess container);
}
