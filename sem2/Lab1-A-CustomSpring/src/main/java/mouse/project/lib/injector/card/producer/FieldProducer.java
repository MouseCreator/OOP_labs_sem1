package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.access.CardAccess;

public interface FieldProducer {
    void setField(Object obj, CardAccess container);
}
