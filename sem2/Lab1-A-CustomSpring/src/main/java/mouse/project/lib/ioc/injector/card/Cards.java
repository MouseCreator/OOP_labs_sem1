package mouse.project.lib.ioc.injector.card;

import mouse.project.lib.ioc.injector.card.container.CardContainer;
import mouse.project.lib.ioc.injector.card.container.CardContainerImpl;
import mouse.project.lib.ioc.injector.card.container.Implementation;
import mouse.project.lib.ioc.injector.card.definition.DefinedCard;
import mouse.project.lib.ioc.injector.card.factory.CardDefinitions;
import mouse.project.lib.ioc.injector.card.factory.CardDefinitionsImpl;
import mouse.project.lib.ioc.injector.card.factory.CardFactory;
import mouse.project.lib.ioc.injector.card.factory.CardFactoryImpl;
import mouse.project.lib.ioc.injector.card.scan.CardScanner;
import mouse.project.lib.ioc.injector.card.scan.DefinedCardScanner;
import mouse.project.lib.ioc.injector.card.scan.DefinitionsManager;

import java.util.Collection;

public class Cards {
    private final CardFactory cardFactory;
    private final DefinitionsManager definitionsManager;
    private final CardScanner cardScanner;
    private Cards(CardFactory cardFactory, DefinitionsManager definitionsManager, CardScanner cardScanner) {
        this.cardFactory = cardFactory;
        this.definitionsManager = definitionsManager;
        this.cardScanner = cardScanner;
    }

    public static Cards create() {
        CardDefinitions definitions = new CardDefinitionsImpl();
        CardContainer container = new CardContainerImpl();
        CardFactory factory = new CardFactoryImpl(container, definitions);
        DefinitionsManager manager = new DefinitionsManager(definitions);
        CardScanner scanner = new DefinedCardScanner();
        return new Cards(factory, manager, scanner);
    }

    public void init(Collection<Class<?>> classes) {
        classes.forEach(c -> {
            DefinedCard<?> card = cardScanner.scan(c);
            definitionsManager.onAdd(card);
        });
    }
    public <T> T get(Class<T> clazz) {
        return cardFactory.buildCard(new Implementation<>(clazz, null));
    }
    public <T> T getNamed(Class<T> clazz, String name) {
        return cardFactory.buildCard(new Implementation<>(clazz, name));
    }
    public <T> Collection<T> getAll(Class<T> clazz) {
        return cardFactory.buildAllCards(new Implementation<>(clazz, null));
    }
    public <T> Collection<T> getAllNamed(Class<T> clazz, String name) {
        return cardFactory.buildAllCards(new Implementation<>(clazz, name));
    }
}