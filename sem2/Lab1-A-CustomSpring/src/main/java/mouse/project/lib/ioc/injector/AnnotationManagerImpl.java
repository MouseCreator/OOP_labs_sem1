package mouse.project.lib.ioc.injector;

import mouse.project.lib.annotation.Controller;
import mouse.project.lib.annotation.Dao;
import mouse.project.lib.annotation.Service;
import mouse.project.lib.exception.ScanException;

import java.lang.annotation.Annotation;
import java.util.List;

public class AnnotationManagerImpl implements AnnotationManager {

    private final List<Class<? extends Annotation>> targets =
            List.of(
                Controller.class,
                Dao.class,
                Service.class
            );
    @Override
    public List<Class<? extends Annotation>> getTargetAnnotations() {
        return targets;
    }

    @Override
    public void validateService(Class<?> clazz) {
        if(isService(clazz))
            return;
        throw new ScanException(clazz + " is not a service.");
    }

    private boolean isService(Class<?> clazz) {
        return targets.stream().anyMatch(clazz::isAnnotationPresent);
    }
}