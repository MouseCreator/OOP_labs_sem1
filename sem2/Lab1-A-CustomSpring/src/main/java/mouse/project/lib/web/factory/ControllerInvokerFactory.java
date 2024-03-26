package mouse.project.lib.web.factory;

import mouse.project.lib.web.invoker.ControllerInvoker;

import java.lang.reflect.Method;

public interface ControllerInvokerFactory {
    ControllerInvoker create(Object controller, Method method);
}
