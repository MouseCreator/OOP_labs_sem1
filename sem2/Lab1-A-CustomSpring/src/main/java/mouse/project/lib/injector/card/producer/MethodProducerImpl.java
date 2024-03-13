package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.container.CardContainer;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.invoke.MethodInvoker;
import mouse.project.lib.injector.card.invoke.ParameterDefinition;
import mouse.project.lib.injector.card.invoke.Parameters;

import java.util.ArrayList;
import java.util.List;

public class MethodProducerImpl implements MethodProducer {
    private final MethodInvoker methodInvoker;
    public MethodProducerImpl(MethodInvoker methodInvoker) {
        this.methodInvoker = methodInvoker;
    }
    @Override
    public Object call(CardContainer container) {
        Implementation<?> origin = methodInvoker.getOrigin();
        Parameters parameters = methodInvoker.getParameters();
        List<ParameterDefinition> parameterDefinitions = parameters.getParameterDefinitions();
        List<Object> args = new ArrayList<>();
        for (ParameterDefinition param : parameterDefinitions) {
            Object implementation = container.findImplementation(param.type());
            args.add(implementation);
        }
        return methodInvoker.invoke(origin, args);
    }
}
