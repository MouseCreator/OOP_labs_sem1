package mouse.project.lib.data.executor;

import java.util.function.Consumer;

public interface AdjustedResult<T> {
    T get();
    AdjustedResult<T> apply(Consumer<T> action);
}
