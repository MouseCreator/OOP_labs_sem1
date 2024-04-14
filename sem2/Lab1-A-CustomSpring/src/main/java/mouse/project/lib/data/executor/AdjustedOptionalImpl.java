package mouse.project.lib.data.executor;

import java.util.Optional;
import java.util.function.Consumer;

public class AdjustedOptionalImpl<T> implements AdjustedOptional<T> {

    private final T model;

    public AdjustedOptionalImpl(T model) {
        this.model = model;
    }

    @Override
    public AdjustedOptional<T> apply(Consumer<T> consumer) {
        if (model == null) {
            return this;
        }
        consumer.accept(model);
        return this;
    }

    @Override
    public Optional<T> get() {
        return Optional.ofNullable(model);
    }
}
