package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardAccess;

public interface FieldProducer {
    void setField(Object obj, CardAccess container);
}
