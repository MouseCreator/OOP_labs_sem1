package mouse.project.lib.injector.card.invoke;

import mouse.project.lib.injector.card.container.Implementation;

public interface FieldInjection {
    void setField(Object toChange, Object setTo);
    Implementation<?> getRequiredType();
}
