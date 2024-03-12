package mouse.project.lib.injector.card.container;

import java.util.Optional;

public class CardContainerImpl implements CardContainer {
    @Override
    public <T> boolean containsImplementation(Implementation<T> implementation) {
        return false;
    }

    @Override
    public <T> T findImplementation(Implementation<T> implementation) {
        return null;
    }

    @Override
    public <T> Optional<T> getImplementation(Implementation<T> implementation) {
        return null;
    }

    @Override
    public void put(Object obj) {

    }
}
