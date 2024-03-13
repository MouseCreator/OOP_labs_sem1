package mouse.project.lib.injector.card.invoke;

import mouse.project.lib.injector.card.container.Implementation;

public interface ParameterDefinition {
    int order();
    Implementation<?> type();
}
