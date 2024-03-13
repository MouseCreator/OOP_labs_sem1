package mouse.project.lib.injector.card.definition;

import mouse.project.lib.exception.CardException;
import mouse.project.lib.injector.card.builder.Builder;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.producer.CardProducer;
import mouse.project.lib.injector.card.requirement.RequirementSet;
import mouse.project.lib.injector.card.requirement.RequirementSetImpl;

import java.util.ArrayList;
import java.util.List;

public class DefinedCardImpl<T> implements DefinedCard<T> {
    private ConstructorDefinition<T> primaryConstructor;
    private final List<SetterDefinition> setterDefinitions;
    private final List<FieldDefinition> fieldDefinitions;
    private final Class<T> origin;
    private final RequirementSet requirementSet;
    public DefinedCardImpl(Class<T> origin) {
        this.origin = origin;
        requirementSet = new RequirementSetImpl();
        primaryConstructor = null;
        setterDefinitions = new ArrayList<>();
        fieldDefinitions = new ArrayList<>();
    }

    @Override
    public ConstructorDefinition<T> getConstructor() {
        return primaryConstructor;
    }

    @Override
    public List<SetterDefinition> getSetters() {
        return new ArrayList<>(setterDefinitions);
    }

    @Override
    public List<FieldDefinition> getFields() {
        return new ArrayList<>(fieldDefinitions);
    }

    @Override
    public void addSetter(SetterDefinition setterDefinition) {
        requirementSet.addAll(setterDefinition.requiredImplementations());
        setterDefinitions.add(setterDefinition);
    }

    @Override
    public void addField(FieldDefinition fieldDefinition) {
        requirementSet.add(fieldDefinition.getImplementation());
        fieldDefinitions.add(fieldDefinition);
    }

    @Override
    public void setPrimaryConstructor(ConstructorDefinition<T> constructor) {
        if (primaryConstructor != null) {
            throw new CardException("Primary constructor is already defined for " + origin);
        }
        this.primaryConstructor = constructor;
        requirementSet.addAll(constructor.requiredImplementations());
    }

    @Override
    public CardProducer<T> getProducer() {
        Builder builder = Builder.getInstance();
        return builder.fromCard(this);
    }

    @Override
    public List<Implementation<?>> requiredImplementations() {
        return requirementSet.getRequirements();
    }
}
