package mouse.project.lib.injector.sources.constructor;

import java.lang.reflect.Method;

public interface SetterRequirement extends MultiRequirement {
    void initWith(Method method);
    Object setInto(Object obj);
}
