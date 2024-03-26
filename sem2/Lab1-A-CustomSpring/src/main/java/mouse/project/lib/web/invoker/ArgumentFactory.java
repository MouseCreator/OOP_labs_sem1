package mouse.project.lib.web.invoker;

import mouse.project.lib.web.invoker.desc.ArgumentDesc;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
public interface ArgumentFactory {
    ArgumentDesc createArgument(Method method, Parameter parameter);
}
