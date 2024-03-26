package mouse.project.lib.web.invoker.processor;

import mouse.project.lib.web.invoker.desc.ArgumentDesc;
import mouse.project.lib.web.request.RequestURL;

public interface URLArgumentProcessor extends ArgumentProcessor{
    Object process(ArgumentDesc argumentDesc, RequestURL requestURL);
}
