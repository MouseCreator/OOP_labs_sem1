package mouse.project.lib.web.dispatcher;

import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Prototype;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.config.WebConfig;
import mouse.project.lib.web.context.WebContext;
import mouse.project.lib.web.exception.RequestProcessException;
import mouse.project.lib.web.parse.JacksonBodyParser;
import mouse.project.lib.web.register.RequestMethod;
import mouse.project.lib.web.request.RequestBody;
import mouse.project.lib.web.request.RequestBodyImpl;
import mouse.project.lib.web.request.RequestURL;
import mouse.project.lib.web.request.RequestURLImpl;
import mouse.project.lib.web.response.WebResponse;
import mouse.project.lib.web.tool.FullURL;
import mouse.project.lib.web.tool.URLService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
@Prototype
public class ReqRespContextImpl implements ReqRespContext {
    private final WebContext webContext;
    private final URLService urlService;
    private final JacksonBodyParser json;
    @Auto
    public ReqRespContextImpl(WebContext webContext, URLService urlService, JacksonBodyParser json) {
        this.webContext = webContext;
        this.urlService = urlService;
        this.json = json;
    }

    @Override
    public void useAndExecute(RequestMethod method, HttpServletRequest req, HttpServletResponse resp) {
        RequestURL requestURL = createRequest(req, method);
        WebDispatcher dispatcher = webContext.getDispatcher(WebConfig.class);
        WebResponse webResponse = dispatcher.onRequest(requestURL);
        Object result = webResponse.getResult();
        String toWrite = json.unparse(result);
        resp.setStatus(webResponse.status());
        try {
            resp.getWriter().write(toWrite);
            resp.flushBuffer();
        } catch (IOException e) {
            throw new RequestProcessException(e);
        }
    }

    private RequestURL createRequest(HttpServletRequest req, RequestMethod method) {
        RequestBody requestBody;
        try {
            String bodyString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            requestBody = new RequestBodyImpl(bodyString);
        } catch (IOException e) {
            throw new RequestProcessException(e);
        }
        String strUrl = getFullURL(req);
        FullURL fullURL = urlService.create(strUrl);
        return new RequestURLImpl(fullURL, method, requestBody);
    }

    private static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
