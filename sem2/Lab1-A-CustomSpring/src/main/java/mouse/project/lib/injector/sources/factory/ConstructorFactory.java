package mouse.project.lib.injector.sources.factory;

import mouse.project.lib.exception.CardException;
import mouse.project.lib.injector.card.container.CardContainer;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.sources.RequiredClass;
import mouse.project.lib.injector.sources.producer.ClassProducer;

import java.util.List;
import java.util.Optional;

public class ConstructorFactory<T> implements Factory<T>{
    private ClassProducer<T> classProducer;
    @Override
    public T create(CardContainer cardContainer) {
        List<RequiredClass> requiredClasses = classProducer.getRequiredClasses();
        for (RequiredClass requiredClass : requiredClasses) {
            Class<?> clazz = requiredClass.getRequiredClass();
            Implementation<?> target;
            if (requiredClass.hasNamed()) {
                target = new Implementation<>(clazz, requiredClass.getNamed());
            } else {
                target = new Implementation<>(clazz);
            }
            Optional<?> implementationOptional = cardContainer.getImplementation(target);
            if (implementationOptional.isPresent()) {
                classProducer.satisfy(requiredClass, implementationOptional.get());
            } else {
                throw new CardException("Cannot build card with constructor. " +
                        "Unsatisfied requirement found: " + requiredClass);
            }
        }
        return classProducer.produceClass();
    }
}
