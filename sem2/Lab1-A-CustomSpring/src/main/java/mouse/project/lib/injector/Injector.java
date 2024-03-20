package mouse.project.lib.injector;

import mouse.project.lib.injector.card.Cards;

public interface Injector {
    String getName();
    Class<?> getConfigurationClass();
    Cards getCards();
}
