package mouse.project.lib.injector;

import java.util.HashMap;
import java.util.Map;

public class ConfiguredInjector {

    private final Map<Class<?>, Injector> savedConfigurations;
    private static ConfiguredInjector configuredInjector = null;
    private ConfiguredInjector() {
        savedConfigurations = new HashMap<>();
    }
    public ConfiguredInjector get() {
        if (configuredInjector == null) {
            configuredInjector = new ConfiguredInjector();
        }
        return configuredInjector;
    }
    public Injector create(Class<?> configurationClass) {
        Injector config = savedConfigurations.get(configurationClass);
        if (config == null) {
            return createNewConfiguration(configurationClass);
        }
        return config;
    }

    private Injector createNewConfiguration(Class<?> configurationClass) {
        InjectorFactory injectorFactory = new InjectorFactoryImpl();
        Injector injector = injectorFactory.createFromConfiguration(configurationClass);
        savedConfigurations.put(configurationClass, injector);
        return injector;
    }

}
