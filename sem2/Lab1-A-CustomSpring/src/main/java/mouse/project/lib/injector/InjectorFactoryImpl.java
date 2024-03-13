package mouse.project.lib.injector;

import mouse.project.lib.annotation.Configuration;
import mouse.project.lib.utils.Utils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.annotation.Annotation;
import java.util.*;

public class InjectorFactoryImpl implements InjectorFactory {
    @Override
    public Injector createFromConfiguration(Class<?> configClass) {
        return createNewConfiguration(configClass);
    }

    private Injector createNewConfiguration(Class<?> configurationClass) {
        Configuration configuration = Utils.getAnnotation(configurationClass, Configuration.class);
        String basePackage = configuration.basePackage();
        Class<?>[] loadHelpers = configuration.loadFrom();
        return null;
    }
    private Set<Class<?>> scanBasePackage(String packageName) {
        AnnotationManager annotationManager = new AnnotationManagerImpl();
        List<Class<? extends Annotation>> targetAnnotations = annotationManager.getTargetAnnotations();
        return scanBasePackageFor(packageName, targetAnnotations);
    }
    private Set<Class<?>> scanBasePackageFor(String packageName,
                                             Collection<Class<? extends Annotation>> annotationClasses) {
        Reflections reflections = new Reflections(packageName, Scanners.TypesAnnotated);

        Set<Class<?>> annotatedClasses = new HashSet<>();
        for (Class<? extends Annotation> annotationClass : annotationClasses) {
            annotatedClasses.addAll(reflections.getTypesAnnotatedWith(annotationClass));
        }

        return annotatedClasses;
    }


}
