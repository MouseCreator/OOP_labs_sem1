package mouse.project.lib.injector;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Data
public class InjectorBase {
    private final String name;
    private final Class<?> configurationClass;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Set<Class<?>> includedClasses;

    public InjectorBase(Class<?> configurationClass, String name) {
        this.configurationClass = configurationClass;
        this.name = name;
        includedClasses = new HashSet<>();
    }

    public boolean isNamed() {
        return !name.isEmpty();
    }

    public void include(Class<?> clazz) {
        includedClasses.add(clazz);
    }
}
