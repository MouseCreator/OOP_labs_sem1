package mouse.project.lib.injector.card.container;

public class Implementations {
    public static <T> Implementation<T> create(Class<T> tClass) {
        ImplementationCreator implementationCreator = new ImplementationCreatorImpl();
        return implementationCreator.getImplementation(tClass);
    }
}
