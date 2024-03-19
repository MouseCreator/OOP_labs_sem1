package mouse.project.lib.injector.card.invoke;

import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.ParameterDefinition;

import java.util.List;

public interface Parameters extends Iterable<ParameterDefinition> {
    List<ParameterDefinition> getParameterDefinitions();
    int size();
    List<Implementation<?>> toRequirements();
}
