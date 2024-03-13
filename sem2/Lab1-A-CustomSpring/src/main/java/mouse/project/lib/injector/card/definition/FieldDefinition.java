package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.producer.FieldProducer;

import java.lang.reflect.Field;

public interface FieldDefinition extends DefinitionWithType {
    Field getField();
    FieldProducer toProducer();
}
