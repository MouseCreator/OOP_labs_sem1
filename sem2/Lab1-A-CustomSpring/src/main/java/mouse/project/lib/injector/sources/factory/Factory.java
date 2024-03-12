package mouse.project.lib.injector.sources.factory;

public interface Factory {
    Class<?> forClass();
    Object create();
}
