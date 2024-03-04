package mouse.project.lib.injector;

import java.lang.annotation.Annotation;
import java.util.List;

public interface AnnotationManager {
    List<Class<? extends Annotation>> getTargetAnnotations();
}
