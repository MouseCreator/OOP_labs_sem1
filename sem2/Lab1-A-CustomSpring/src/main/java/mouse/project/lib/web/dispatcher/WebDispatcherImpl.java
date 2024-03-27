package mouse.project.lib.web.dispatcher;

import mouse.project.lib.web.invoker.ControllerInvoker;
import mouse.project.lib.web.response.WebResponse;
import mouse.project.lib.web.request.RequestURL;
import mouse.project.lib.web.tool.FullURL;

public class WebDispatcherImpl implements WebDispatcher {
    public WebResponse onRequest(RequestURL requestURL) {
        ControllerInvoker invoker = getInvoker(requestURL.getURL());
        Object invoked = invoker.invoke(requestURL);
        return toResponse(invoked);
    }

    private WebResponse toResponse(Object invoked) {
        return null;
    }

    private ControllerInvoker getInvoker(FullURL address) {
        return null;
    }
}
