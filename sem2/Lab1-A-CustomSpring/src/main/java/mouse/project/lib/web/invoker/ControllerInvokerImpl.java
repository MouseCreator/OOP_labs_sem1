package mouse.project.lib.web.invoker;

import mouse.project.lib.web.exception.ControllerException;
import mouse.project.lib.web.request.RequestURL;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerInvokerImpl implements ControllerInvoker {

    private final Object controller;
    private final Method method;
    public ControllerInvokerImpl(Object controller,
                                 Method method) {
        this.controller = controller;
        this.method = method;
    }

    @Override
    public Object invoke(RequestURL requestURL) {

        Object[] res = getMethodParams();
        method.setAccessible(true);
        try {
            return method.invoke(controller, res);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ControllerException(e);
        }
    }

    private Object[] getMethodParams() {
        return new Object[0];
    }

}
