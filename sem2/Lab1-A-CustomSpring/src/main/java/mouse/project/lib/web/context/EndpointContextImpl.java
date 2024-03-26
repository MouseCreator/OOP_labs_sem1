package mouse.project.lib.web.context;

import mouse.project.lib.web.tool.URLTool;

import java.lang.reflect.Method;

public class EndpointContextImpl implements EndpointContext {

    private final Method method;
    private final URLTool urlTool;

    public EndpointContextImpl(Method method, URLTool urlTool) {
        this.method = method;
        this.urlTool = urlTool;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public URLTool getUrl() {
        return urlTool;
    }
}
