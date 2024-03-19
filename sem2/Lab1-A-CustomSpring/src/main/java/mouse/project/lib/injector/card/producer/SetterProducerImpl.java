package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardAccess;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.invoke.MethodInvoker;
import mouse.project.lib.injector.card.invoke.ParameterDefinition;
import mouse.project.lib.injector.card.invoke.Parameters;

import java.util.ArrayList;
import java.util.List;

public class SetterProducerImpl implements SetterProducer {
    private final MethodInvoker methodInvoker;
    public SetterProducerImpl(MethodInvoker methodInvoker) {
        this.methodInvoker = methodInvoker;
    }

    @Override
    public void apply(Object applyTo, CardAccess container) {
        Parameters parameters = methodInvoker.getParameters();
        List<Object> args = new ArrayList<>();
        for (ParameterDefinition param : parameters) {
            Implementation<?> type = param.type();
            Object implementation = container.getImplementation(type);
            args.add(implementation);
        }
        methodInvoker.invoke(applyTo, args);
    }
}
