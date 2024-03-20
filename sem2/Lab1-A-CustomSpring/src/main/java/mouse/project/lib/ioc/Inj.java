package mouse.project.lib.ioc;

import java.util.Collection;

public interface Inj {
    <T> T get(Class<T> clazz);
    <T> T get(Class<T> clazz, String name);
    <T> Collection<T> getAll(Class<T> clazz);
    <T> Collection<T> getAll(Class<T> clazz, String name);
}
