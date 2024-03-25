package mouse.project.lib.web.scan;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.utils.Scanners;
import mouse.project.lib.web.annotation.RequestPrefix;
import mouse.project.lib.web.annotation.URL;
import mouse.project.lib.web.exception.ControllerException;
import mouse.project.lib.web.register.ControllerInvoker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
public class ControllerScan {
    @Auto
    private List<WebAnnotationProcessor> annotationProcessors;
    public void scanControllers() {
        List<Object> allControllers = getAllControllers();
        for (Object controller : allControllers) {
            processController(controller);
        }
    }

    private void processController(Object controller) {
        Class<?> clazz = controller.getClass();
        RequestPrefix prefixAnnotation = clazz.getAnnotation(RequestPrefix.class);
        String prefix = "/";
        if (prefixAnnotation != null) {
            prefix += prefixAnnotation.value();
        }
        Collection<Method> methods = Scanners.getMethodsAnnotatedWith(clazz, URL.class);
        for (Method method : methods) {
            String fullUrl = prefix + "/" + method.getAnnotation(URL.class).value();
            fullUrl = fullUrl.replaceAll("//", "/");
            Collection<Registration> regs = getInvoker(fullUrl, controller, method);
            if (regs.isEmpty()) {
                throw new ControllerException("Method " + method + " has @URL annotation, but cannot be processed." +
                        " Make sure at least one of CRUD annotations is present.");
            }
        }
    }

    private Collection<Registration> getInvoker(String url, Object controller, Method method) {
        List<Registration> registrations = new ArrayList<>();
        for (WebAnnotationProcessor processor : annotationProcessors) {
            if (processor.canProcess(method)) {
                registrations.add(processor.process(url, controller, method));
            }
        }
        return registrations;
    }

    private List<Object> getAllControllers() {
        return List.of();
    }
}
