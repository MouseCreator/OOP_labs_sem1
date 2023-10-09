package univ.lab.inject;

public interface Injector {
    <T> T getInstance(Class<T> interfaceClass);
    void addImplementation(Class<?> interfaceClass, Class<?> implClass);
}
