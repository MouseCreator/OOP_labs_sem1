package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.producer.CardProducer;

public interface CardDefinition<T> extends Definition {
    CardProducer<T> getProducer();
}