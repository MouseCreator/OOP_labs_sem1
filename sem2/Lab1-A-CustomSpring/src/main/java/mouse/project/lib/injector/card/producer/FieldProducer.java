package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;

public interface FieldProducer<T> {
    void setField(T obj, CardContainer container);
}
