package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.producer.ConstructorProducer;

import java.lang.reflect.Constructor;

public interface ConstructorDefinition<T> extends DefinitionWithParameters {
    ConstructorProducer<T> toProducer();
    Constructor<T> getConstructor();
}
