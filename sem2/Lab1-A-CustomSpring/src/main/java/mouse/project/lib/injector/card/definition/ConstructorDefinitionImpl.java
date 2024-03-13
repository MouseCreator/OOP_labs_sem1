package mouse.project.lib.injector.card.definition;

import mouse.project.lib.injector.card.builder.Builder;
import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.producer.ConstructorProducer;
import mouse.project.lib.injector.card.requirement.RequirementSet;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorDefinitionImpl<T> implements ConstructorDefinition<T> {
    private Constructor<T> constructor;
    private RequirementSet requirementSet;
    @Override
    public ConstructorProducer<T> toProducer() {
        Builder builder = Builder.getInstance();
        return builder.fromConstructor(this);
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public List<Implementation<?>> requiredImplementations() {
        return requirementSet.getRequirements();
    }
}
