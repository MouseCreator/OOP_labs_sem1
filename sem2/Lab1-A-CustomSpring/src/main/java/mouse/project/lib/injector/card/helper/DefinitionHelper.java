package mouse.project.lib.injector.card.helper;

import mouse.project.lib.injector.card.definition.ConstructorDefinition;
import mouse.project.lib.injector.card.definition.FieldDefinition;
import mouse.project.lib.injector.card.definition.SetterDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface DefinitionHelper {
    <T> ConstructorDefinition<T> getConstructor(Constructor<T> constructor);
    FieldDefinition getField(Field field);
    SetterDefinition getSetter(Method method);
}
