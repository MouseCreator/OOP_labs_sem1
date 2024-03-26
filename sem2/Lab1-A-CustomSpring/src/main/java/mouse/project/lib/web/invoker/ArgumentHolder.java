package mouse.project.lib.web.invoker;

import mouse.project.lib.web.invoker.desc.BodyDesc;
import mouse.project.lib.web.invoker.desc.ParameterDesc;

public interface ArgumentHolder {
    boolean isBodySource();
    boolean isParamSource();
    BodyDesc toBodyDesc();
    ParameterDesc toParamDesc();
}
