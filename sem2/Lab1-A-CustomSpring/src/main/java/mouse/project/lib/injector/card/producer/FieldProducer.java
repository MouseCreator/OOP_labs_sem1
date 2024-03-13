package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;

public interface FieldProducer {
    void setField(Object obj, CardContainer container);
}
