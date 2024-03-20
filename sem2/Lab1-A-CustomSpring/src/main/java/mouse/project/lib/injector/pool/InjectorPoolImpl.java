package mouse.project.lib.injector.pool;

import mouse.project.lib.injector.Injector;
import mouse.project.lib.injector.InjectorBase;
import mouse.project.lib.injector.configuration.ConfigurationScanner;

import java.util.HashMap;
import java.util.Map;

public class InjectorPoolImpl implements InjectorPool {
    private final Map<Class<?>, Injector> map;
    private final ConfigurationScanner configurationScanner;
    public InjectorPoolImpl() {
        this.map = new HashMap<>();
        configurationScanner = new ConfigurationScanner();
    }

    public Injector request(Class<?> configuration) {
        Injector injector = map.get(configuration);
        if (injector==null) {
            InjectorBase base = configurationScanner.scan(configuration);
            injector = base.build();
            map.put(injector.getConfigurationClass(), injector);
        }
        return injector;
    }

    public void free(Class<?> configuration) {
        map.remove(configuration);
    }
}
