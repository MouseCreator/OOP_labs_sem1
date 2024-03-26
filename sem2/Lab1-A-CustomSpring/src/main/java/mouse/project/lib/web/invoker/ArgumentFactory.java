package mouse.project.lib.web.invoker;

import mouse.project.lib.web.context.EndpointContext;
import mouse.project.lib.web.invoker.desc.ArgumentDesc;

import java.lang.reflect.Parameter;
public interface ArgumentFactory {
    ArgumentDesc createArgument(EndpointContext context, Parameter parameter);
}
