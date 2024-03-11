package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RequirementProviderImpl implements RequirementProvider {
    @Override
    public <T> ConstructorRequirement<T> getConstructor(Constructor<T> constructor) {
        return ConstructorRequirementImpl.get(constructor);
    }

    @Override
    public FieldRequirement getField(Field field) {
        FieldRequirement fieldRequirement = new FieldRequirementImpl();
        fieldRequirement.initWith(field);
        return fieldRequirement;
    }

    @Override
    public SetterRequirement getSetter(Method method) {
        SetterRequirement setterRequirement = new SetterRequirementImpl();
        setterRequirement.initWith(method);
        return setterRequirement;
    }
}
