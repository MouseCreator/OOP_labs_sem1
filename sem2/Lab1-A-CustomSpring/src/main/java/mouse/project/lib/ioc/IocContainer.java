package mouse.project.lib.ioc;

import java.util.List;
import java.util.Set;

public interface IocContainer {
    <T> T getSingle(Class<? extends T> targetClass);
    <T> T getNamed(Class<? extends T> targetClass, String name);
    <T> List<T> getList(Class<T> targetClass);
    <T> Set<T> getSet(Class<T> targetClass);
}
