package mouse.project.lib.web.endpoint;

import mouse.project.lib.web.tool.URLTool;

import java.lang.reflect.Method;

public interface Endpoints {
    URLTool getURL(Method method);
}
