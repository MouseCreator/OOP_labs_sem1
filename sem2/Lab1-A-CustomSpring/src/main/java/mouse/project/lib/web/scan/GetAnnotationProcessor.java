package mouse.project.lib.web.scan;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.Get;
import mouse.project.lib.web.factory.ControllerInvokerFactory;
import mouse.project.lib.web.register.ControllerInvoker;
import mouse.project.lib.web.register.RequestType;

import java.lang.reflect.Method;
@Service
public class GetAnnotationProcessor implements WebAnnotationProcessor {

    private final ControllerInvokerFactory controllerInvokerFactory;
    @Auto
    public GetAnnotationProcessor(ControllerInvokerFactory controllerInvokerFactory) {
        this.controllerInvokerFactory = controllerInvokerFactory;
    }

    @Override
    public boolean canProcess(Method method) {
        return method.isAnnotationPresent(Get.class);
    }

    @Override
    public Registration process(String url, Object controller, Method method) {
        ControllerInvoker invoker = controllerInvokerFactory.create(controller, method);
        return new Registration(url, RequestType.GET, invoker);
    }
}
