package mouse.project.lib.injector.card.definition;


import mouse.project.lib.injector.card.container.Implementation;

public interface FactoryCard<T> extends CardDefinition<T> {
    MethodDefinition getFactoryMethod();
    Implementation<T> getType();
}
