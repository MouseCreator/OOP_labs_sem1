package mouse.project.lib.web.factory;

import mouse.project.lib.web.context.ControllerContext;
import mouse.project.lib.web.context.ControllerContextImpl;
import mouse.project.lib.web.context.EndpointContext;
import mouse.project.lib.web.tool.URLTool;

import java.lang.reflect.Method;

public class ContextFactoryImpl implements ContextFactory {
    @Override
    public ControllerContext getControllerContext(Class<?> rootClass) {
        URLTool urlTool = createURL(rootClass);
        return new ControllerContextImpl(rootClass, urlTool);
    }

    private URLTool createURL(Class<?> rootClass) {
        return null;
    }

    @Override
    public EndpointContext getEndpointContext(ControllerContext parentContext, Method method) {
        return null;
    }
}
