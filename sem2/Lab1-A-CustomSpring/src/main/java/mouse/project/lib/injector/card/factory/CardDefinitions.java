package mouse.project.lib.injector.card.factory;

import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.CardDefinition;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface CardDefinitions {
    CardDefinition<?> lookup(Implementation<?> implementation);
    void add(CardDefinition<?> definition);
    Collection<CardDefinition<?>> lookupAll(Implementation<?> implementation);
    Collection<CardDefinition<?>> getAnnotatedWith(Class<? extends Annotation> annotation);
}
