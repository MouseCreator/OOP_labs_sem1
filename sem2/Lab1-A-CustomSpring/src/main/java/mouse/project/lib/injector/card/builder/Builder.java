package mouse.project.lib.injector.card.builder;

import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.*;
import mouse.project.lib.injector.card.invoke.*;
import mouse.project.lib.injector.card.producer.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Builder {
    private static Builder instance = null;

    public static Builder getInstance() {
        if (instance == null) {
            instance = new Builder();
        }
        return instance;
    }

    private Builder() {
    }

    public <T> ConstructorProducer<T> fromConstructor(ConstructorDefinition<T> definition) {
        Constructor<T> constructor = definition.getConstructor();
        Parameters parameters = definition.getParameters();
        ConstructorInvoker<T> constructorInvoker = new ConstructorInvokerImpl<>(constructor, parameters);
        return new ConstructorProducerImpl<>(constructorInvoker);
    }

    public SetterProducer fromSetter(SetterDefinition definition) {
        Method method = definition.getMethod();
        Parameters parameters = definition.getParameters();
        MethodInvoker methodInvoker = new MethodInvokerImpl(method, parameters);
        return new SetterProducerImpl(methodInvoker);
    }

    public FieldProducer fromField(FieldDefinition definition) {
        Field field = definition.getField();
        Implementation<?> implementation = definition.getImplementation();
        FieldInjection fieldInjection = new FieldInjectionImpl(field, implementation);
        return new FieldProducerImpl(fieldInjection);
    }

    public <T> CardProducer<T> fromCard(DefinedCard<T> cardDefinition) {
        ConstructorDefinition<T> constructor = cardDefinition.getConstructor();
        DefinedProducer<T> definedProducer = new DefinedProducerImpl<>();
        definedProducer.setConstructor(fromConstructor(constructor));

        List<SetterDefinition> setters = cardDefinition.getSetters();
        setters.forEach(s -> definedProducer.addSetter(fromSetter(s)));
        List<FieldDefinition> fields = cardDefinition.getFields();
        fields.forEach(f -> definedProducer.addFieldInjection(fromField(f)));

        return definedProducer;
    }
}
