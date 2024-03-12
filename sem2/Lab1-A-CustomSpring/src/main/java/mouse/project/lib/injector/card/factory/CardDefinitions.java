package mouse.project.lib.injector.card.factory;

import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.CardDefinition;

public interface CardDefinitions {
    <T> CardDefinition<T> lookup(Implementation<T> current);
}
