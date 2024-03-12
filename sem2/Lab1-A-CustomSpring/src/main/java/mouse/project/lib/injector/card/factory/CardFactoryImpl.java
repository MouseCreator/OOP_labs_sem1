package mouse.project.lib.injector.card.factory;

import mouse.project.lib.exception.CardException;
import mouse.project.lib.injector.card.container.CardContainer;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.CardDefinition;
import mouse.project.lib.injector.sources.factory.Factory;

import java.util.ArrayList;
import java.util.List;

public class CardFactoryImpl {
    private CardContainer container;
    private CardDefinitions cardDefinitions;
    public <T> T buildCard(Implementation<T> implementation) {
        return buildCard(implementation, new BuildStack());
    }

    private static class BuildStack {
        private final List<Implementation<?>> stack;

        public BuildStack() {
            this.stack = new ArrayList<>();
        }
        public BuildStack next(Implementation<?> current) {
            BuildStack next = new BuildStack();
            next.stack.addAll(stack);
            next.stack.add(current);
            return next;
        }

        @Override
        public String toString() {
            return stack.toString();
        }

        public <T> boolean contains(Implementation<T> current) {
            return stack.contains(current);
        }
    }
    private <T> T buildCard(Implementation<T> current, BuildStack buildStack) {
        if (buildStack.contains(current)) {
            throw new CardException("Loop in card factory: " + buildStack + " looped back to " + current);
        }
        if (container.containsImplementation(current)) {
            return container.findImplementation(current);
        }

        CardDefinition<T> definition = cardDefinitions.lookup(current);
        List<Implementation<?>> implementations = definition.requiredImplementations();

        for (Implementation<?> implementation : implementations) {
            if (container.containsImplementation(implementation)) {
                continue;
            }
            buildCard(implementation, buildStack.next(current));
        }
        Factory<T> factory = definition.getFactory();
        T obj = factory.create(container);
        container.put(obj);
        return obj;
    }
}
