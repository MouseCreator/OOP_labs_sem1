package mouse.project.lib.injector.card.factory;

import mouse.project.lib.exception.MultipleImplementationsException;
import mouse.project.lib.exception.NoCardDefinitionException;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.CardDefinition;
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
            throw new NoCardDefinitionException("No card definition for " + clazz);
        }
        return cardDefinitions;
    }

    private CardDefinition<?> getPrimaryFromList(Class<?> clazz, String name) {
        List<CardDefinition<?>> list = getNamedDefinitions(clazz, name);
        List<CardDefinition<?>> primary = getPrimaryFromList(list);
        if (primary.size()==1) {
            return primary.get(0);
        }
        throw new MultipleImplementationsException("Found multiple definitions for " + clazz +
                " and name " + name + ": " + primary.size());
    }

    private List<CardDefinition<?>> getNamedDefinitions(Class<?> clazz, String name) {
        List<CardDefinition<?>> cardDefinitions = getDefinitionsByClass(clazz);
        return cardDefinitions.stream().filter(t -> name.equals(t.getType().getName())).toList();
    }

    @Override
    public void add(CardDefinition<?> definition) {
        Implementation<?> type = definition.getType();
        Class<?> origin = type.getClazz();
        Class<?> current = origin;
        while (true) {
            addToMap(current, definition);
            if (current.equals(Object.class)) {
                break;
            } else {
                current = current.getSuperclass();
            }
        }
        Set<Class<?>> interfaces = new InterfaceUtils().getAllInterfaces(origin);
        for (Class<?> interfaceI : interfaces) {
            addToMap(interfaceI, definition);
        }

    }

    private static class InterfaceUtils {
        public Set<Class<?>> getAllInterfaces(Class<?> clazz) {
            Set<Class<?>> interfaces = new HashSet<>();
            getAllInterfaces(clazz, interfaces);
            return interfaces;
        }
        private void getAllInterfaces(Class<?> clazz, Set<Class<?>> interfaces) {
            Class<?>[] clazzInterfaces = clazz.getInterfaces();
            for (Class<?> interfaceI : clazzInterfaces) {
                if (interfaces.add(interfaceI)) {
                    getAllInterfaces(interfaceI, interfaces);
                }
            }
            if (clazz.getSuperclass() != null) {
                getAllInterfaces(clazz.getSuperclass(), interfaces);
            }
        }
    }

    private <T> void addToMap(Class<?> current, CardDefinition<T> definition) {
        List<CardDefinition<?>> cardDefinitions = map.computeIfAbsent(current, c -> new ArrayList<>());
        cardDefinitions.add(definition);
    }

    @Override
    public Collection<CardDefinition<?>> lookupAll(Implementation<?> implementation) {
        if (implementation.isNamed()) {
            return getNamed(implementation.getClazz(), implementation.getName());
        }
        return getAll(implementation.getClazz());
    }

    private Collection<CardDefinition<?>> getAll(Class<?> clazz) {
        return getDefinitionsByClass(clazz);
    }

    private Collection<CardDefinition<?>> getNamed(Class<?> clazz, String name) {
        return getNamedDefinitions(clazz, name);
    }

}
