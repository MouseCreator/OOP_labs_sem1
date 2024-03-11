package mouse.project.lib.injector.sources;

import lombok.Data;

@Data
public class RequiredClass {
    private final Class<?> requiredClass;
    private final String named;
    public RequiredClass(Class<?> required, String named) {
        this.requiredClass = required;
        this.named = named;
    }

    public RequiredClass(Class<?> required) {
        requiredClass = required;
        named = null;
    }

    public boolean hasNamed() {
        return named != null;
    }
}
