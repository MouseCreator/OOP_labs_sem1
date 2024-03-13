package mouse.project.lib.injector.card.producer;

import mouse.project.lib.exception.CardException;
import mouse.project.lib.injector.card.container.CardContainer;

public class FactoryMethodProducerImpl<T> implements FactoryMethodProducer<T> {
    private MethodProducer<T> methodProducer;
    private final Class<T> clazz;

    public FactoryMethodProducerImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T produce(CardContainer container) {
        Object obj = methodProducer.call(container);
        try {
            return clazz.cast(obj);
        } catch (Exception e) {
            throw new CardException("Cannot cast to target class: " + clazz + " object " + obj);
        }
    }

    @Override
    public void setMethod(MethodProducer<T> method) {
        this.methodProducer = method;
    }
}
