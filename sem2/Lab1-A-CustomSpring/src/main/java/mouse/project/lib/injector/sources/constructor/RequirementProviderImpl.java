package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Constructor;

public class RequirementProviderImpl implements RequirementProvider {
    @Override
    public <T> ConstructorRequirement<T> getConstructor(Constructor<T> constructor) {
        return ConstructorRequirementImpl.get(constructor);
    }
}
