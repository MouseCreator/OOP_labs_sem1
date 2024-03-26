package mouse.project.lib.web.dispatcher;

import mouse.project.lib.web.response.WebResponse;
import mouse.project.lib.web.request.RequestURL;

public interface WebDispatcher {
    WebResponse onRequest(RequestURL requestURL);
}
