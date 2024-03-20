package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.builder.Builder;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.invoke.Parameters;
import mouse.project.lib.injector.card.producer.ActionProducer;

import java.lang.reflect.Method;
import java.util.List;

public class ActionDefinitionImpl implements ActionDefinition {
    private final Method method;
    private final Parameters methodParameters;

    @Override
    public Method getMethod() {
        return method;
    }

    public ActionDefinitionImpl(Method method, Parameters methodParameters) {
        this.method = method;
        this.methodParameters = methodParameters;
    }

    @Override
    public ActionProducer toProducer() {
        Builder builder = Builder.getInstance();
        return builder.fromAction(this);
    }

    @Override
    public List<Implementation<?>> requiredImplementations() {
        return methodParameters.toRequirements();
    }

    @Override
    public Parameters getParameters() {
        return methodParameters;
    }
}
