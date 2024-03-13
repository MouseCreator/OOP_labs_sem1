package mouse.project.lib.injector.card.helper;

import mouse.project.lib.injector.card.container.Implementation;
import mouse.project.lib.injector.card.definition.*;
import mouse.project.lib.injector.card.invoke.Parameters;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DefinitionHelperImpl implements DefinitionHelper {
    private final DefinitionsInspector definitionsInspector;

    public DefinitionHelperImpl() {
        definitionsInspector = new DefinitionsInspector();
    }

    @Override
    public <T> ConstructorDefinition<T> getConstructor(Constructor<T> constructor) {
        Parameters parameters = definitionsInspector.inspectConstructor(constructor);
        return new ConstructorDefinitionImpl<>(constructor, parameters);
    }
    @Override
    public FieldDefinition getField(Field field) {
        Implementation<?> implementation = definitionsInspector.inspectField(field);
        return new FieldDefinitionImpl(field, implementation);
    }

    @Override
    public SetterDefinition getSetter(Method method) {
        Parameters parameters = definitionsInspector.inspectMethod(method);
        return new SetterDefinitionImpl(method, parameters);
    }
}
