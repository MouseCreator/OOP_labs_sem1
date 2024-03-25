package mouse.project.lib.ioc.injector.map;

import mouse.project.lib.exception.MultipleImplementationsException;
import mouse.project.lib.exception.NoCardDefinitionException;
import mouse.project.lib.ioc.injector.card.container.Implementation;

import java.util.*;

public class DefinedMapImpl<E extends TypeHolder<?>> implements DefinedMap<E> {
    private final Map<Class<?>, List<E>> map;

    public DefinedMapImpl() {
        map = new HashMap<>();
    }

    public E lookup(Implementation<?> implementation) {
        if (implementation.isNamed()) {
            return getPrimaryFromList(implementation.getClazz(), implementation.getName());
        }
        return getPrimaryFromList(implementation.getClazz());
    }

    private E getPrimaryFromList(Class<?> clazz) {
        List<E> cardDefinitions = getDefinitionsByClass(clazz);
        List<E> primary = getPrimaryFromList(cardDefinitions);
        if (primary.size()==1) {
            return primary.get(0);
        }
        throw new MultipleImplementationsException("Found multiple definitions for " + clazz + ": " + primary.size());
    }
    private List<E> getDefinitionsByClass(Class<?> clazz) {
        List<E> cardDefinitions = map.get(clazz);
        if (cardDefinitions == null || cardDefinitions.isEmpty()) {
            throw new NoCardDefinitionException("No definition for " + clazz + " in map");
        }
        return cardDefinitions;
    }

    private E getPrimaryFromList(Class<?> clazz, String name) {
        List<E> list = getNamedDefinitions(clazz, name);
        List<E> primary = getPrimaryFromList(list);
        if (primary.size()==1) {
            return primary.get(0);
        }
        throw new MultipleImplementationsException("Found multiple definitions for " + clazz +
                " and name " + name + ": " + primary.size());
    }

    private List<E> getPrimaryFromList(List<E> cardDefinitions) {
        if (cardDefinitions.size()==1) {
            return List.of(cardDefinitions.get(0));
        }
        List<E> list = cardDefinitions.stream().filter(t -> t.getType().isPrimary()).toList();
        if (list.size()==1) {
            return List.of(list.get(0));
        }
        return list;
    }

    private List<E> getNamedDefinitions(Class<?> clazz, String name) {
        List<E> cardDefinitions = getDefinitionsByClass(clazz);
        return filterNamedOnly(name, cardDefinitions);
    }

    private List<E> filterNamedOnly(String name, List<E> cardDefinitions) {
        return cardDefinitions.stream().filter(t -> name.equals(t.getType().getName())).toList();
    }

    public void add(E definition) {
        Implementation<?> type = definition.getType();
        Class<?> origin = type.getClazz();
        Class<?> current = origin;
        if (map.containsKey(origin)) {
            return;
        }
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

    private void addToMap(Class<?> current, E definition) {
        List<E> cardDefinitions = map.computeIfAbsent(current, c -> new ArrayList<>());
        cardDefinitions.add(definition);
    }

    public Collection<E> lookupAll(Implementation<?> implementation) {
        if (implementation.isNamed()) {
            return getNamed(implementation.getClazz(), implementation.getName());
        }
        return getAll(implementation.getClazz());
    }

    @Override
    public <T> boolean contains(Implementation<T> implementation) {
        List<E> es = map.get(implementation.getClazz());
        if (es == null || es.isEmpty()) {
            return false;
        }
        if (!implementation.isNamed()) {
            return !es.isEmpty();
        }
        List<E> namedOnly = filterNamedOnly(implementation.getName(), es);
        return !namedOnly.isEmpty();
    }

    private Collection<E> getAll(Class<?> clazz) {
        return getDefinitionsByClass(clazz);
    }

    private Collection<E> getNamed(Class<?> clazz, String name) {
        return getNamedDefinitions(clazz, name);
    }
}
