package mouse.project.lib.ioc;

import java.util.List;
import java.util.Set;

public interface IocContainer {
    <T> T getSingle(Class<? extends T> targetClass);
    <T> List<T> getList(Class<? extends T> targetClass);
    <T> Set<T> getSet(Class<? extends T> targetClass);
}
