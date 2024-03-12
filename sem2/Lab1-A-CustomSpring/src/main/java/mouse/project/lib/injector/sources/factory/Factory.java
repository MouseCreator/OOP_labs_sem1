package mouse.project.lib.injector.sources.factory;

import mouse.project.lib.injector.card.container.CardContainer;

public interface Factory<T> {
    T create(CardContainer cardContainer);
}
