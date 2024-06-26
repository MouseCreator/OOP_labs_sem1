package mouse.project.lib.web.dispatcher;

import mouse.project.lib.ioc.annotation.Prototype;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.exception.StatusException;
import mouse.project.lib.web.invoker.ControllerInvoker;
import mouse.project.lib.web.register.RequestMethod;
import mouse.project.lib.web.response.WebResponse;
import mouse.project.lib.web.request.RequestURL;
import mouse.project.lib.web.response.WebResponseImpl;
import mouse.project.lib.web.tool.FullURL;
@Service
@Prototype
public class WebDispatcherImpl implements WebDispatcher {
    private DispatcherMap dispatcherMap = null;

    public WebResponse onRequest(RequestURL requestURL) {
        int status = 200;
        Object invoked = null;
        try {
            ControllerInvoker invoker = getInvoker(requestURL.getURL(), requestURL.method());
            invoked = invoker.invoke(requestURL);
        } catch (StatusException statusException) {
            status = statusException.getStatus();
        }
        return toResponse(status, invoked);
    }

    @Override
    public void useMap(DispatcherMap dispatcherMap) {
        this.dispatcherMap = dispatcherMap;
    }

    private WebResponse toResponse(int status, Object invoked) {
        return new WebResponseImpl(status, invoked);
    }

    private ControllerInvoker getInvoker(FullURL address, RequestMethod method) {
        return dispatcherMap.getInvoker(address, method);
    }
}
