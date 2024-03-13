package mouse.project.lib.injector.card.definition;



public interface FactoryCard<T> extends CardDefinition<T> {
    MethodDefinition getFactoryMethod();
}
