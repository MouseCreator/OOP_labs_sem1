package mouse.project.lib.data.executor;

import java.util.List;
import java.util.function.Consumer;

public interface AdjustedResultList<T> {
    AdjustedResultList<T> apply(Consumer<T> consumer);
    List<T> get();
}
