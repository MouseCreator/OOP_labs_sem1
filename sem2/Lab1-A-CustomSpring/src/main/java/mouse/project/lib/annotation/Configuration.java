package mouse.project.lib.annotation;

import mouse.project.lib.modules.MouseModules;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
    /**
     *
     * @return base package to scan
     */
    String basePackage() default "";

    /**
     *
     * @return classes that has to be added to IoC container
     */
    Class<?>[] includeClasses() default {};
    MouseModules[] includeModules() default {};

    String name() default "";
}
