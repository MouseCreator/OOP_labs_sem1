package mouse.project.lib.annotation;

import mouse.project.lib.modules.MouseModules;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
    String basePackage() default "";
    Class<?>[] loadFrom() default {};
    MouseModules[] includeModules() default {};
}
