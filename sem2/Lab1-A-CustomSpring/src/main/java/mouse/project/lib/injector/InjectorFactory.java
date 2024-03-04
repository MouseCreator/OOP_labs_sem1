package mouse.project.lib.injector;

public interface InjectorFactory {
    Injector createFromConfiguration(Class<?> configClass);
}
