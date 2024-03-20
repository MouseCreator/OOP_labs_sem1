package mouse.project.lib.injector.card.scan;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.annotation.Annotation;
import java.util.Set;

public class PackageLoader {
    public Set<Class<?>> getAnnotatedClasses(String prefix, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(prefix, Scanners.TypesAnnotated);
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
