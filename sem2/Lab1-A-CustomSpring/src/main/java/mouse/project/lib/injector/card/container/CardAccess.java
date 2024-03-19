package mouse.project.lib.injector.card.container;

public interface CardAccess {
    <T> T getImplementation(Implementation<T> implementation);
}
