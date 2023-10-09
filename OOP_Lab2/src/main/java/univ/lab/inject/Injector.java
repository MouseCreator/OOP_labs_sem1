package univ.lab.inject;

public interface Injector {
    <E extends T, T> E getInstance(Class<T> interfaceClass);
}
