package mouse.project.lib.injector.sources.constructor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(ElementType.CONSTRUCTOR)
public @interface Construct {
    String key();
}
