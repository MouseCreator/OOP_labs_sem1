package mouse.project.lib.data.executor;


import java.util.Optional;
import java.util.function.Consumer;

public interface AdjustedOptional<T> {
    AdjustedOptional<T> apply(Consumer<T> consumer);
    Optional<T> get();
}
