package mouse.project.lib.web.register;

import mouse.project.lib.web.scan.Registration;

public interface ControllerRegister {
    void add(RequestType requestType, String fullUrl, ControllerInvoker invoker);
    void add(Registration registration);
}
