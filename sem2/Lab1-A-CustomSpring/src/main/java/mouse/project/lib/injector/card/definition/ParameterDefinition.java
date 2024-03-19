package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.container.Implementation;

public interface ParameterDefinition extends Collected {
    int order();
    Implementation<?> type();

}
