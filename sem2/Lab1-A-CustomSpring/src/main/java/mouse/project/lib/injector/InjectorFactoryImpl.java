package mouse.project.lib.injector;

import mouse.project.lib.annotation.Configuration;

import java.lang.annotation.Annotation;

public class InjectorFactoryImpl implements InjectorFactory {
    @Override
    public Injector createFromConfiguration(Class<?> configClass) {
        return createNewConfiguration(configClass);
    }

    private Injector createNewConfiguration(Class<?> configurationClass) {
        Configuration configuration = getAnnotation(configurationClass, Configuration.class);
        String basePackage = configuration.basePackage();
        return null;
    }

    private <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotation) {
        T configuration = clazz.getAnnotation(annotation);
        if (configuration == null) {
            throw new IllegalArgumentException(
                    String.format("Configuration class %s does not have %s annotation present.",
                            clazz.getName(), annotation.getName()));
        }
        return configuration;
    }
}
