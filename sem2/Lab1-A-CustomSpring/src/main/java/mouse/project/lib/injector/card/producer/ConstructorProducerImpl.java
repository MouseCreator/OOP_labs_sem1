package mouse.project.lib.injector.card.producer;

import mouse.project.lib.exception.CardException;
import mouse.project.lib.injector.card.container.CardAccess;
import mouse.project.lib.injector.card.container.CardContainer;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.invoke.ConstructorInvoker;
import mouse.project.lib.injector.card.invoke.ParameterDefinition;
import mouse.project.lib.injector.card.invoke.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConstructorProducerImpl<T> implements ConstructorProducer<T> {
    private final ConstructorInvoker<T> invoker;

    public ConstructorProducerImpl(ConstructorInvoker<T> invoker) {
        this.invoker = invoker;
    }

    @Override
    public T construct(CardAccess container) {
        Parameters parameters = invoker.getParameters();
        List<ParameterDefinition> params = parameters.getParameterDefinitions();
        List<Object> args = new ArrayList<>();
        for (ParameterDefinition parameter : params) {
            Implementation<?> type = parameter.type();
            Object implementation = container.getImplementation(type);
            args.add(implementation);
        }
        return invoker.invoke(args);
    }
}
