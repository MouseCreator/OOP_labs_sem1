package mouse.project.lib.injector.card.invoke;

import java.util.List;

public interface ActionInvoker {
    Object invoke(List<Object> parameters);
    Parameters getParameters();
}
