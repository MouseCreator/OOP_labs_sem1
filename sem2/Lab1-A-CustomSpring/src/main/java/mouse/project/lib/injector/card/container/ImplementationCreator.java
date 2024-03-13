package mouse.project.lib.injector.card.container;

public interface ImplementationCreator {
    <T> Implementation<T> getImplementation(Class<T> tClass);
}
