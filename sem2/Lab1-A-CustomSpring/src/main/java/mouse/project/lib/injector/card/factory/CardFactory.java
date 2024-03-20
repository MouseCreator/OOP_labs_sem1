package mouse.project.lib.injector.card.factory;

import mouse.project.lib.injector.card.container.Implementation;

import java.util.Collection;

public interface CardFactory {
    <T> T buildCard(Implementation<T> implementation);
    <T> Collection<T> buildAllCards(Implementation<T> implementation);
    <T> T buildCard(Implementation<T> implementation, BuildStack buildStack);
    <T> Collection<T> buildAllCards(Implementation<T> implementation, BuildStack buildStack);
}
