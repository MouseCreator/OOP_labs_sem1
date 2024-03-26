package mouse.project.lib.web.invoker;

import mouse.project.lib.web.url.RequestParameter;

import java.util.Collection;

public interface ControllerInvoker {
    Object invoke(Collection<RequestParameter> parameters);
}
