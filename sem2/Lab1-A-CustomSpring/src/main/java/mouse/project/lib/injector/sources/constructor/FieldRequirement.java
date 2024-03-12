package mouse.project.lib.injector.sources.constructor;

import mouse.project.lib.injector.sources.RequiredClass;

import java.lang.reflect.Field;

public interface FieldRequirement extends Requirement {
    void initWith(Field field);
    void injectInto(Object toInject);
    void satisfy(Object with);
    RequiredClass getRequired();
}
