package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

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
}
