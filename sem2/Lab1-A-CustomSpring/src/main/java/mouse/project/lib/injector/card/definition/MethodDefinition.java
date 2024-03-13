package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.producer.MethodProducer;

import java.lang.reflect.Method;

public interface MethodDefinition extends DefinitionWithParameters, DefinitionWithType {
    Method getMethod();
    MethodProducer toProducer();
    Implementation<?> getOrigin();
}
