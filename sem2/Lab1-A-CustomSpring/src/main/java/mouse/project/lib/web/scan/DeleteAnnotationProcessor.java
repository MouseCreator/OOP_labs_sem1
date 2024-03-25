package mouse.project.lib.web.scan;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.Delete;
import mouse.project.lib.web.factory.ControllerInvokerFactory;
import mouse.project.lib.web.register.ControllerInvoker;
import mouse.project.lib.web.register.RequestType;

import java.lang.reflect.Method;
@Service
public class DeleteAnnotationProcessor implements WebAnnotationProcessor {

    private final ControllerInvokerFactory cif;
    @Auto
    public DeleteAnnotationProcessor(ControllerInvokerFactory cif) {
        this.cif = cif;
    }

    @Override
    public boolean canProcess(Method method) {
        return method.isAnnotationPresent(Delete.class);
    }

    @Override
    public Registration process(String url, Object controller, Method method) {
        ControllerInvoker invoker = cif.create(controller, method);
        return new Registration(url, RequestType.DELETE, invoker);
    }
}
