package mouse.project.lib.web.dispatcher;

import mouse.project.lib.web.invoker.ControllerInvoker;
import mouse.project.lib.web.response.WebResponse;
import mouse.project.lib.web.request.RequestAddress;
import mouse.project.lib.web.request.RequestURL;

public class WebDispatcherImpl implements WebDispatcher {
    public WebResponse onRequest(RequestURL requestURL) {
        RequestAddress address = requestURL.getAddress();

        ControllerInvoker invoker = getInvoker(address);
        Object invoked = invoker.invoke(requestURL.getParameters(), requestURL.getBody());
        return toResponse(invoked);
    }

    private WebResponse toResponse(Object invoked) {
        return null;
    }

    private ControllerInvoker getInvoker(RequestAddress address) {
        return null;
    }
}
