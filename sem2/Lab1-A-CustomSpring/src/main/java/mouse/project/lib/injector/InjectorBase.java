package mouse.project.lib.injector;

import lombok.Data;

@Data
public class InjectorBase {
    private final String name;
    private final Class<?> configurationClass;

    public InjectorBase(Class<?> configurationClass, String name) {
        this.configurationClass = configurationClass;
        this.name = name;
    }

    public boolean isNamed() {
        return !name.isEmpty();
    }

    public void include(Class<?> clazz) {

    }
}
