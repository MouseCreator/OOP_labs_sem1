package mouse.project.lib.injector.card.producer;


import mouse.project.lib.injector.card.access.CardAccess;

public interface ConstructorProducer<T> {
    T construct(CardAccess container);
}
