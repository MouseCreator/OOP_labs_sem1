package mouse.project.lib.ioc.injector;

import mouse.project.lib.annotation.Controller;
import mouse.project.lib.annotation.Dao;
import mouse.project.lib.annotation.Service;

import java.lang.annotation.Annotation;
import java.util.List;

public class AnnotationManagerImpl implements AnnotationManager {
    @Override
    public List<Class<? extends Annotation>> getTargetAnnotations() {
        return List.of(
                Controller.class,
                Dao.class,
                Service.class
        );
    }
}
