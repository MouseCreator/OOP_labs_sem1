package mouse.project.lib.web.dispatcher;

import mouse.project.lib.ioc.annotation.Prototype;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.web.register.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Prototype
public interface ReqRespContext {
    void useAndExecute(RequestMethod method, HttpServletRequest req, HttpServletResponse resp);
}
