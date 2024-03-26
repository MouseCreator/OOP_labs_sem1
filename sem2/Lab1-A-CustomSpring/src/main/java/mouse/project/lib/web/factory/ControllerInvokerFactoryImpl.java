package mouse.project.lib.web.factory;

import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.invoker.*;

import java.lang.reflect.Method;

@Service
public class ControllerInvokerFactoryImpl implements ControllerInvokerFactory {

    @Override
    public ControllerInvoker create(Object controller, Method method) {
        return null;
    }
}
