package mouse.project.lib.ioc.injector.card.scan;

import mouse.project.lib.exception.ScanException;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.annotation.Annotation;
import java.util.Set;

public class PackageLoader {
    public Set<Class<?>> getAnnotatedClasses(String prefix, Class<? extends Annotation> annotation) {
        if (prefix.isEmpty()) {
            throw new ScanException("Cannot scan configuration with no base package specified");
        }
        Reflections reflections = new Reflections(prefix, Scanners.TypesAnnotated);
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
