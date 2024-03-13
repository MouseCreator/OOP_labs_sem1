package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.producer.SetterProducer;

import java.lang.reflect.Method;

public interface SetterDefinition extends DefinitionWithParameters {
    Method getMethod();
    SetterProducer toProducer();
}
