package mouse.project.lib.web.invoker.creator;

import mouse.project.lib.web.invoker.desc.ArgumentDesc;
import mouse.project.lib.web.invoker.ArgumentSource;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface DescCreator {
    ArgumentDesc create(Method method, Parameter parameter);
    boolean accepts(ArgumentSource source);
}
