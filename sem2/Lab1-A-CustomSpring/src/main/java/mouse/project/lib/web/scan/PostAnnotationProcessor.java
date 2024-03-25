package mouse.project.lib.web.scan;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.annotation.Post;
import mouse.project.lib.web.factory.ControllerInvokerFactory;
import mouse.project.lib.web.register.ControllerInvoker;
import mouse.project.lib.web.register.RequestType;

import java.lang.reflect.Method;
@Service
public class PostAnnotationProcessor implements WebAnnotationProcessor {
    private final ControllerInvokerFactory factory;
    @Auto
    public PostAnnotationProcessor(ControllerInvokerFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean canProcess(Method method) {
        return method.isAnnotationPresent(Post.class);
    }

    @Override
    public Registration process(String url, Object controller, Method method) {
        ControllerInvoker invoker = factory.create(controller, method);
        return new Registration(url, RequestType.POST, invoker);
    }
}
