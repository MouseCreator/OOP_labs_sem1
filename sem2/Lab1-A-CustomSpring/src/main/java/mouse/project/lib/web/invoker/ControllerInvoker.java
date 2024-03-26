package mouse.project.lib.web.invoker;

import mouse.project.lib.web.request.RequestBody;
import mouse.project.lib.web.request.RequestParameter;

import java.util.Collection;

public interface ControllerInvoker {
    Object invoke(Collection<RequestParameter> parameters, RequestBody body);
}
