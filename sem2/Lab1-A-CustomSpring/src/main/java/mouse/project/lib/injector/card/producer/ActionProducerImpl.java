package mouse.project.lib.injector.card.producer;

import mouse.project.lib.injector.card.access.CardAccess;
import mouse.project.lib.injector.card.helper.ParameterCreator;
import mouse.project.lib.injector.card.invoke.ActionInvoker;
import mouse.project.lib.injector.card.invoke.Parameters;

import java.util.List;

public class ActionProducerImpl implements ActionProducer {

    private final ActionInvoker actionInvoker;

    public ActionProducerImpl(ActionInvoker actionInvoker) {
        this.actionInvoker = actionInvoker;
    }

    @Override
    public void call(CardAccess container) {
        Parameters parameters = actionInvoker.getParameters();
        ParameterCreator parameterCreator = new ParameterCreator(container);
        List<Object> args = parameterCreator.assignAll(parameters);
        actionInvoker.invoke(args);
    }
}
