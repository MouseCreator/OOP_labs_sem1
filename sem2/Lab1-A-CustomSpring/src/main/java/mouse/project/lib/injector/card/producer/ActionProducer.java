package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.access.CardAccess;

public interface ActionProducer {
    void call(Object callOn, CardAccess container);
}
