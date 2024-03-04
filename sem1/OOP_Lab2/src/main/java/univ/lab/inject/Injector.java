package univ.lab.inject;

public interface Injector {
    /**
     *
     * @param interfaceClass - interface, instance of which to get
     * @return implementation of the interface
     * @param <T> - interface class
     */
    <T> T getInstance(Class<T> interfaceClass);

    /**
     *
     * @param interfaceClass - interface to implement
     * @param implClass - implementation of the interface
     * @return previous implementation or null if it is absent
     */
    Class<?> addImplementation(Class<?> interfaceClass, Class<?> implClass);
    Class<?> addImplementation(Class<?> interfaceClass, Object implementation);
}
