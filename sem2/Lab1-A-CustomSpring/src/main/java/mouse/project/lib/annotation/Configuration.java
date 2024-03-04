package mouse.project.lib.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
    String basePackage() default "";
    Class<?>[] loadFrom() default {};
}
