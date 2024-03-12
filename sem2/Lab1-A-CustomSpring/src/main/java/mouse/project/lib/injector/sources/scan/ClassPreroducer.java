package mouse.project.lib.injector.sources.scan;

import mouse.project.lib.injector.sources.constructor.ConstructorRequirement;
import mouse.project.lib.injector.sources.constructor.FieldRequirement;
import mouse.project.lib.injector.sources.constructor.SetterRequirement;

public interface ClassPreroducer<T> {
    void setPrimaryConstructor(ConstructorRequirement<T> constructor);
    void addSetter(SetterRequirement setterRequirement);
    void addFieldRequirements(FieldRequirement fieldRequirement);
}
