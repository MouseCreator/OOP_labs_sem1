package mouse.project.lib.web.url;

import java.util.Collection;

public interface RequestURL {
    Collection<RequestParameter> getParameters();
    RequestAddress getAddress();
    String getURL();
}
