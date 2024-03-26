package mouse.project.lib.web.request;


import java.util.Collection;

public interface RequestURL {
    Collection<RequestParameter> getParameters();
    RequestAddress getAddress();
    String getURL();
    RequestBody getBody();
}
