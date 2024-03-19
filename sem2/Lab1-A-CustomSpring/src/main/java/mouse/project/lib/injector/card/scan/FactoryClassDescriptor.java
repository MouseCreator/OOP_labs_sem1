package mouse.project.lib.injector.card.scan;

import mouse.project.lib.injector.card.definition.CardDefinition;

import java.util.List;

public class FactoryClassDescriptor<T> {
    private CardDefinition<T> selfDefinition;
    private List<CardDefinition<?>> factoryDefinitions;
}
