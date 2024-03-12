package mouse.project.lib.injector.card.container;

import lombok.Data;
@Data
public class Implementation<T> {
    private final Class<T> clazz;
    private final String name;
    public Implementation(Class<T> clazz) {
        this.clazz = clazz;
        name = null;
    }

    public Implementation(Class<T> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public boolean isNamed() {
        return name != null;
    }
    public Class<T> getRequiredClass() {
        return clazz;
    }
}
