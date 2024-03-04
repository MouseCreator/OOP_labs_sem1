package mouse.project.lib.injector.sources;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ClassDescriptor {
    private final Class<?> toConstruct;
    private final List<RequiredClass> requiredClasses;
    public ClassDescriptor(Class<?> toConstruct) {
        this.toConstruct = toConstruct;
        this.requiredClasses = new ArrayList<>();
    }

    public ClassDescriptor(Class<?> toConstruct, List<RequiredClass> requiredClasses) {
        this.toConstruct = toConstruct;
        this.requiredClasses = new ArrayList<>(requiredClasses);
    }

    public void addRequired(RequiredClass requiredClass) {
        requiredClasses.add(requiredClass);
    }
}
