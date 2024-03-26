package mouse.project.lib.web.factory;

import mouse.project.lib.web.annotation.RequestPrefix;
import mouse.project.lib.web.context.ControllerContext;
import mouse.project.lib.web.context.ControllerContextImpl;
import mouse.project.lib.web.context.EndpointContext;
import mouse.project.lib.web.exception.ControllerException;
import mouse.project.lib.web.tool.SimpleURLTool;
import mouse.project.lib.web.tool.URLTool;

import java.lang.reflect.Method;

public class ContextFactoryImpl implements ContextFactory {
    @Override
    public ControllerContext getControllerContext(Class<?> rootClass) {
        URLTool urlTool = createURL(rootClass);
        return new ControllerContextImpl(rootClass, urlTool);
    }

    private URLTool createURL(Class<?> rootClass) {
        RequestPrefix annotation = rootClass.getAnnotation(RequestPrefix.class);
        if (annotation == null) {
            throw new ControllerException("Controller " + rootClass + " has no request prefix.");
        }
        String value = annotation.value();
        return SimpleURLTool.from(value);
    }

    @Override
    public EndpointContext getEndpointContext(ControllerContext parentContext, Method method) {
        return null;
    }
}
