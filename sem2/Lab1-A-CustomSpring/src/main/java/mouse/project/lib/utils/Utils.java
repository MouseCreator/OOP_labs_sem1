package mouse.project.lib.utils;

import java.lang.annotation.Annotation;

public class Utils {
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotation) {
        T configuration = clazz.getAnnotation(annotation);
        if (configuration == null) {
            throw new IllegalArgumentException(
                    String.format("Configuration class %s does not have %s annotation present.",
                            clazz.getName(), annotation.getName()));
        }
        return configuration;
    }
}
