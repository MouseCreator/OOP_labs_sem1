package mouse.project.lib.injector.card.invoke;

import java.util.List;

public interface Parameters extends Iterable<ParameterDefinition> {
    List<ParameterDefinition> getParameterDefinitions();
    int size();
}
