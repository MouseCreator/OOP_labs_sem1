package mouse.project.lib.injector.card.definition;

import java.util.List;

public interface DefinedCard<T> extends CardDefinition<T> {
    ConstructorDefinition<T> getConstructor();
    List<SetterDefinition> getSetters();
    List<FieldDefinition> getFields();

    void addSetter(SetterDefinition setterDefinition);
    void addField(FieldDefinition fieldDefinition);
    void setPrimaryConstructor(ConstructorDefinition<T> constructor);
}
