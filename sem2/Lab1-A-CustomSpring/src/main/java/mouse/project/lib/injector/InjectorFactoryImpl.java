package mouse.project.lib.injector;

import mouse.project.lib.annotation.Configuration;
import mouse.project.lib.utils.Utils;

import java.lang.annotation.Annotation;

public class InjectorFactoryImpl implements InjectorFactory {
    @Override
    public Injector createFromConfiguration(Class<?> configClass) {
        return createNewConfiguration(configClass);
    }

    private Injector createNewConfiguration(Class<?> configurationClass) {
        Configuration configuration = Utils.getAnnotation(configurationClass, Configuration.class);
        String basePackage = configuration.basePackage();
        return null;
    }


}
