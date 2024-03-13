package mouse.project.lib.injector.card.producer;

import mouse.project.lib.exception.CardException;
import mouse.project.lib.injector.card.container.CardContainer;

import java.util.ArrayList;
import java.util.List;
public class DefinedProducerImpl<T> implements DefinedProducer<T> {
    private ConstructorProducer<T> constructorProducer;
    private final List<SetterProducer<T>> setterProducerList;
    private final List<FieldProducer<T>> fieldProducerList;

    public DefinedProducerImpl() {
        constructorProducer = null;
        setterProducerList = new ArrayList<>();
        fieldProducerList = new ArrayList<>();
    }

    @Override
    public T produce(CardContainer container) {
        if (constructorProducer == null) {
            throw new CardException("Producer is not defined");
        }
        T object = constructorProducer.construct(container);
        setterProducerList.forEach(sp -> sp.apply(object, container));
        fieldProducerList.forEach(sp -> sp.setField(object, container));
        return object;
    }

    @Override
    public void setConstructor(ConstructorProducer<T> constructor) {
        this.constructorProducer = constructor;
    }

    @Override
    public void addSetter(SetterProducer<T> setterProducer) {
        this.setterProducerList.add(setterProducer);
    }

    @Override
    public void addFieldInjection(FieldProducer<T> fieldProducer) {
        this.fieldProducerList.add(fieldProducer);
    }
}
