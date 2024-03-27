package mouse.project.lib.web.dispatcher;

import mouse.project.lib.web.invoker.ControllerInvoker;
import mouse.project.lib.web.tool.FullURL;

public interface DispatcherMap {
    void setInvoker(String url, ControllerInvoker invoker);
    void setInvoker(FullURL url, ControllerInvoker invoker);
    ControllerInvoker getInvoker(String url);
}
