package mouse.project.lib.web.scan;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.Update;
import mouse.project.lib.web.factory.ControllerInvokerFactory;
import mouse.project.lib.web.register.ControllerInvoker;
import mouse.project.lib.web.register.RequestType;

import java.lang.reflect.Method;
@Service
public class UpdateAnnotationProcessor implements WebAnnotationProcessor {

    private final ControllerInvokerFactory cif;
    @Auto
    public UpdateAnnotationProcessor(ControllerInvokerFactory cif) {
        this.cif = cif;
    }

    @Override
    public boolean canProcess(Method method) {
        return method.isAnnotationPresent(Update.class);
    }

    @Override
    public Registration process(String url, Object controller, Method method) {
        ControllerInvoker invoker = cif.create(controller, method);
        return new Registration(url, RequestType.UPDATE, invoker);
    }
}
