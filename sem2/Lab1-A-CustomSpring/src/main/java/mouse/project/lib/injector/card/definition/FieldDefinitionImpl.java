package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.builder.Builder;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.producer.FieldProducer;

import java.lang.reflect.Field;
import java.util.List;

public class FieldDefinitionImpl implements FieldDefinition {

    private final Field field;
    public final Implementation<?> required;
    public FieldDefinitionImpl(Field field, Implementation<?> required) {
        this.field = field;
        this.required = required;
    }

    @Override
    public List<Implementation<?>> requiredImplementations() {
        return List.of(required);
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public FieldProducer toProducer() {
        Builder builder = Builder.getInstance();
        return builder.fromField(this);
    }

    @Override
    public Implementation<?> getType() {
        return required;
    }
}
