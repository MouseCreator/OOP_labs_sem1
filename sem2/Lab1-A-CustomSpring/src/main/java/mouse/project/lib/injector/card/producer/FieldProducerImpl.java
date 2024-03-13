package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.invoke.FieldInjection;

public class FieldProducerImpl implements FieldProducer {
    private final FieldInjection fieldInjection;
    @Override
    public void setField(Object obj, CardContainer container) {
        Implementation<?> requiredType = fieldInjection.getRequiredType();
        Object implementation = container.findImplementation(requiredType);
        fieldInjection.setField(obj, implementation);
    }

    public FieldProducerImpl(FieldInjection fieldInjection) {
        this.fieldInjection = fieldInjection;
    }
}