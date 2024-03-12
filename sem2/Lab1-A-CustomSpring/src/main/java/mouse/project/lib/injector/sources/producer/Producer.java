package mouse.project.lib.injector.sources.producer;

import mouse.project.lib.injector.sources.RequiredClass;
import java.util.List;
public interface Producer {
    List<RequiredClass> getRequiredClasses();
    List<RequiredClass> getUnsatisfiedRequirements();
}
