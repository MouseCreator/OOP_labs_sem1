package mouse.project.lib.injector.card.scan;

import mouse.project.lib.annotation.Service;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PackageLoader {
    public List<Class<?>> getClasses(String prefix, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(prefix, Scanners.TypesAnnotated);
        Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(annotation);
        return new ArrayList<>(allClasses);
    }
}
