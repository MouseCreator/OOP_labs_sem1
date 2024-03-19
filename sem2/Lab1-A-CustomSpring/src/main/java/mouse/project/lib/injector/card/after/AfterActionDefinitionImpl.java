package mouse.project.lib.injector.card.after;

import mouse.project.lib.injector.card.access.CardAccess;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.ActionDefinition;

import java.util.List;

public class AfterActionDefinitionImpl implements AfterActionDefinition {
    private final ActionDefinition methodDefinition;
    public AfterActionDefinitionImpl(ActionDefinition methodDefinition) {
        this.methodDefinition = methodDefinition;
    }

    @Override
    public void callAction(Object callOn, CardAccess access) {
        methodDefinition.toProducer(callOn).call(access);
    }

    @Override
    public List<Implementation<?>> requiredImplementations() {
        return methodDefinition.requiredImplementations();
    }
}
