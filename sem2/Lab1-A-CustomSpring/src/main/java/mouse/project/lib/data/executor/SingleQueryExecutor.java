package mouse.project.lib.data.executor;

import java.util.Collection;
import java.util.Optional;

public interface SingleQueryExecutor {
    void none(String sql);
    <T> T single(String sql, Class<T> mapTo);
    <T> Collection<T> multi(String sql, Class<T> mapTo);
    <T> Optional<T> optional(String sql, Class<T> mapTo);
}
