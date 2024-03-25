package mouse.project.lib.ioc.injector.card.container;

import lombok.*;

@Data
public class Implementation<T> {

    private final Class<T> clazz;
    private final String name;
    private boolean primary;
    private boolean prototype;
    public Implementation(Class<T> clazz) {
        this.clazz = clazz;
        name = null;
        primary = false;
        prototype = false;
    }

    public Implementation(Class<T> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
        this.primary = false;
        prototype = false;
    }

    public boolean isNamed() {
        return name != null;
    }



}
