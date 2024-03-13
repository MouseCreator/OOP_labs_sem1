package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.producer.ConstructorProducer;

public interface ConstructorDefinition<T> extends Definition {
    ConstructorProducer<T> toProducer();
}
