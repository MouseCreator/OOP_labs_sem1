package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Field;

public interface FieldRequirement extends Requirement {
    void initWith(Field field);
    void inject(Object toInject);
    void satisfy(Object with);
}
