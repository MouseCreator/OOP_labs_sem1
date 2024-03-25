package mouse.project.lib.ioc.injector.card.definition;

import mouse.project.lib.ioc.injector.card.container.Implementation;
import mouse.project.lib.ioc.injector.card.producer.CardProducer;

public interface CardDefinition<T> extends Definition {
    CardProducer<T> getProducer();
    Implementation<T> getType();


}
