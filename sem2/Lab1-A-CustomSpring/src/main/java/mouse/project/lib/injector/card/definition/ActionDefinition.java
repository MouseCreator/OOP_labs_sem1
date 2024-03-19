package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.producer.ActionProducer;

import java.lang.reflect.Method;

public interface ActionDefinition extends DefinitionWithParameters {
    ActionProducer toProducer(Object callOn);
    Method getMethod();
}
