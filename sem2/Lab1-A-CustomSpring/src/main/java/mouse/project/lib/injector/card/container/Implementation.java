package mouse.project.lib.injector.card.container;

import lombok.*;

@Data
public class Implementation<T> {

    private final Class<T> clazz;
    private final String name;
    private boolean primary;
    public Implementation(Class<T> clazz) {
        this.clazz = clazz;
        name = null;
        primary = false;
    }

    public Implementation(Class<T> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
        this.primary = false;
    }

    public boolean isNamed() {
        return name != null;
    }



}
