package mouse.project.lib.injector.pool;

import mouse.project.lib.injector.Injector;

public interface InjectorPool {
    Injector request(Class<?> configuration);
    void free(Class<?> configuration);
}
