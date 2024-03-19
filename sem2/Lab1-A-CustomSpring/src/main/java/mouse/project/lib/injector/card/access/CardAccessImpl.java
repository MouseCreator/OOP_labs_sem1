package mouse.project.lib.injector.card.access;

import mouse.project.lib.exception.ImplementationAccessException;
import mouse.project.lib.injector.card.container.CardContainer;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.CardDefinition;
import mouse.project.lib.injector.card.factory.CardDefinitions;

import java.util.*;

public class CardAccessImpl implements CardAccess {
    private final CardDefinitions definitions;
    private final CardContainer container;
    private final Set<Implementation<?>> allowedImplementations;

    public CardAccessImpl(CardDefinitions definitions, CardContainer container,
                          Collection<Implementation<?>> allowedImplementations) {
        this.definitions = definitions;
        this.container = container;
        this.allowedImplementations = new HashSet<>(allowedImplementations);
    }

    @Override
    public <T> T getImplementation(Implementation<T> implementation) {
        assertHasAccessTo(implementation);
        CardDefinition<?> definition = definitions.lookup(implementation);
        return processDefinition(implementation, definition);
    }

    @Override
    public <T> Collection<T> getAllImplementations(Implementation<T> implementation) {
        assertHasAccessTo(implementation);
        Collection<CardDefinition<?>> cardDefinitions = definitions.lookupAll(implementation);
        return cardDefinitions.stream().map(d -> processDefinition(implementation, d)).toList();
    }
    private <T> T processDefinition(Implementation<T> implementation, CardDefinition<?> definition) {
        Implementation<?> type = definition.getType();
        if (type.isPrototype()) {
            return create(definition, implementation);
        } else {
            return handleSingleton(implementation, definition);
        }
    }

    private <T> void assertHasAccessTo(Implementation<T> implementation) {
        if (!allowedImplementations.contains(implementation)) {
            throw new ImplementationAccessException("Denied access to " + implementation);
        }
    }

    private <T> T handleSingleton(Implementation<T> implementation, CardDefinition<?> definition) {
        if(container.containsImplementation(implementation)) {
            return container.findImplementation(implementation);
        }
        T result = create(definition, implementation);
        container.put(result);
        return result;
    }

    private <T> T create(CardDefinition<?> definition, Implementation<T> implementation) {
        Object produce = definition.getProducer().produce(this);
        Class<T> clazz = implementation.getClazz();
        return clazz.cast(produce);
    }
}
