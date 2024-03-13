package mouse.project.lib.injector.card.invoke;


import mouse.project.lib.injector.card.container.Implementation;

import java.util.List;

public interface MethodInvoker {
    Object invoke(Object invokeOn, List<Object> parameters);
    Parameters getParameters();
    Implementation<?> getOrigin();
}
