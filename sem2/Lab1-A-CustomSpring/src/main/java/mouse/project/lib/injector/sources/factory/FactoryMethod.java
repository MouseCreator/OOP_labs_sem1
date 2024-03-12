package mouse.project.lib.injector.sources.factory;

import mouse.project.lib.injector.card.container.CardContainer;
import mouse.project.lib.injector.sources.constructor.FactoryMethodRequirement;

public class FactoryMethod<T> implements Factory<T>{
    private FactoryMethodRequirement<T> factoryMethodRequirement;
    @Override
    public T create(CardContainer cardContainer) {
        return null;
    }

}
