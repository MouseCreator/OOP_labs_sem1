package mouse.project.lib.injector.card.factory;

import mouse.project.lib.injector.card.container.Implementation;

public interface CardFactory {
    <T> T buildCard(Implementation<T> implementation);
}
