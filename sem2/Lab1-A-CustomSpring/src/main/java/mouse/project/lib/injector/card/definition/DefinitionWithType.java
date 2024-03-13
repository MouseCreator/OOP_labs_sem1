package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.container.Implementation;

public interface DefinitionWithType extends Definition {
    Implementation<?> getType();
}
