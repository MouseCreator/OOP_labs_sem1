package mouse.project.lib.injector.card.invoke;

import mouse.project.lib.injector.card.container.Implementation;

public record ParameterDefinitionImpl(Implementation<?> type, int order) implements ParameterDefinition {
}
