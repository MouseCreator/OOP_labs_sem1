package mouse.project.lib.injector.card.invoke;

import mouse.project.lib.exception.CardException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ActionInvokerImpl implements ActionInvoker {
    private final Method method;
    private final Parameters parameters;
    private final Object invokeOn;

    public ActionInvokerImpl(Method method, Parameters parameters, Object invokeOn) {
        this.method = method;
        this.parameters = parameters;
        this.invokeOn = invokeOn;
    }

    @Override
    public Object invoke(List<Object> params) {
        method.setAccessible(true);
        try {
            return method.invoke(invokeOn, params.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CardException("Error invoking method.", e);
        }
    }

    @Override
    public Parameters getParameters() {
        return parameters;
    }
}
