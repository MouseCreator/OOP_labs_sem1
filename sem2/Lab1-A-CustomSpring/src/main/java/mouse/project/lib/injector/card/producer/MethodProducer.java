package mouse.project.lib.injector.card.producer;


import mouse.project.lib.injector.card.access.CardAccess;

public interface MethodProducer {
    Object call(CardAccess container);
}
