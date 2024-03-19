package mouse.project.lib.injector.card.factory;

import mouse.project.lib.exception.MultipleImplementationsException;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.CardDefinition;

import java.lang.annotation.Annotation;
import java.util.*;

public class CardDefinitionsImpl implements CardDefinitions {

    private final Map<Class<?>, List<CardDefinition<?>>> map;

    public CardDefinitionsImpl() {
        map = new HashMap<>();
    }

    @Override
    public CardDefinition<?> lookup(Implementation<?> implementation) {
        if (implementation.isNamed()) {
            return getPrimaryFromList(implementation.getClazz(), implementation.getName());
        }
        return getPrimaryFromList(implementation.getClazz());
    }

    private CardDefinition<?> getPrimaryFromList(Class<?> clazz) {
        List<CardDefinition<?>> cardDefinitions = getDefinitionsByClass(clazz);
        List<CardDefinition<?>> primary = getPrimaryFromList(cardDefinitions);
        if (primary.size()==1) {
            return primary.get(0);
        }
        throw new MultipleImplementationsException("Found multiple definitions for " + clazz + ": " + primary.size());
    }

    private List<CardDefinition<?>> getPrimaryFromList(List<CardDefinition<?>> cardDefinitions) {
        if (cardDefinitions.size()==1) {
            return List.of(cardDefinitions.get(0));
        }
        List<CardDefinition<?>> list = cardDefinitions.stream().filter(t -> t.getType().isPrimary()).toList();
        if (list.size()==1) {
            return List.of(list.get(0));
        }
        return list;
    }

    private <T> List<CardDefinition<?>> getDefinitionsByClass(Class<T> clazz) {
        List<CardDefinition<?>> cardDefinitions = map.get(clazz);
        if (cardDefinitions == null || cardDefinitions.isEmpty()) {
            throw new NoSuchElementException("No card definition for class " + clazz);
        }
        return cardDefinitions;
    }

    private CardDefinition<?> getPrimaryFromList(Class<?> clazz, String name) {
        List<CardDefinition<?>> cardDefinitions = getDefinitionsByClass(clazz);
        List<CardDefinition<?>> list = cardDefinitions.stream().filter(t -> name.equals(t.getType().getName())).toList();
        List<CardDefinition<?>> primary = getPrimaryFromList(list);
        if (primary.size()==1) {
            return primary.get(0);
        }
        throw new MultipleImplementationsException("Found multiple definitions for " + clazz +
                " and name " + name + ": " + primary.size());
    }

    @Override
    public void add(CardDefinition<?> definition) {
        Implementation<?> type = definition.getType();
        Class<?> current = type.getClazz();
        while (true) {
            addToMap(current, definition);
            if (current.equals(Object.class)) {
                break;
            } else {
                current = current.getSuperclass();
            }
        }
        Class<?>[] interfaces = current.getInterfaces();
        for (Class<?> interfaceI : interfaces) {
            addToMap(interfaceI, definition);
        }

    }

    private <T> void addToMap(Class<?> current, CardDefinition<T> definition) {
        List<CardDefinition<?>> cardDefinitions = map.computeIfAbsent(current, c -> new ArrayList<>());
        cardDefinitions.add(definition);
    }

    @Override
    public Collection<CardDefinition<?>> lookupAll(Implementation<?> implementation) {
        return null;
    }

    @Override
    public Collection<CardDefinition<?>> getAnnotatedWith(Class<? extends Annotation> annotation) {
        return null;
    }
}
