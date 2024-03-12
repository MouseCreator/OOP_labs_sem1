package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.sources.factory.Factory;

import java.util.List;

public interface CardDefinition<T> {
    List<Implementation<?>> requiredImplementations();
    Factory<T> getFactory();
}
