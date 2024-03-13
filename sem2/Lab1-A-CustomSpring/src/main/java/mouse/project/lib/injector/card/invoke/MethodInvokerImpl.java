package mouse.project.lib.injector.card.invoke;

import mouse.project.lib.exception.CardException;
import mouse.project.lib.injector.card.container.Implementation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodInvokerImpl implements MethodInvoker{

    private final Method method;
    private final Parameters parameters;
    private final Implementation<?> origin;
    public MethodInvokerImpl(Method method, Parameters parameters, Implementation<?> origin) {
        this.method = method;
        this.parameters = parameters;
        this.origin = origin;
    }

    public MethodInvokerImpl(Method method, Parameters parameters) {
        this.method = method;
        this.parameters = parameters;
        this.origin = null;
    }

    @Override
    public Object invoke(Object invokeOn, List<Object> parameters) {
        method.setAccessible(true);
        try {
            return method.invoke(invokeOn, parameters.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CardException("Error invoking method.", e);
        }
    }

    @Override
    public Parameters getParameters() {
        return parameters;
    }

    @Override
    public Implementation<?> getOrigin() {
        if (origin == null) {
            throw new CardException("No origin defined");
        }
        return origin;
    }
}
