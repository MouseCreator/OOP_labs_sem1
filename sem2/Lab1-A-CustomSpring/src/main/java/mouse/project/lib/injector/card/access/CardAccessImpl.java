package mouse.project.lib.injector.card.access;

import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.factory.BuildStack;
import mouse.project.lib.injector.card.factory.CardFactory;

import java.util.*;

public class CardAccessImpl implements CardAccess {

    private final BuildStack buildStack;
    private final CardFactory factory;

    public CardAccessImpl(BuildStack buildStack, CardFactory factory) {
        this.buildStack = buildStack;
        this.factory = factory;
    }

    @Override
    public <T> T getImplementation(Implementation<T> implementation) {
        return factory.buildCard(implementation, buildStack);
    }

    @Override
    public <T> Collection<T> getAllImplementations(Implementation<T> implementation) {
        return factory.buildAllCards(implementation, buildStack);
    }

}
