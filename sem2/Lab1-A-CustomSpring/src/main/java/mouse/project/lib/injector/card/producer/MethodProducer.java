package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;

public interface MethodProducer {
    Object call(CardContainer container);
}
