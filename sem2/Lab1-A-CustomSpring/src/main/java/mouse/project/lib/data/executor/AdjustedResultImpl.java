package mouse.project.lib.data.executor;

import java.util.function.Consumer;

public class AdjustedResultImpl<T> implements AdjustedResult<T> {

    private final T model;

    public AdjustedResultImpl(T model) {
        this.model = model;
    }

    @Override
    public T get() {
        return model;
    }

    @Override
    public AdjustedResult<T> apply(Consumer<T> action) {
        action.accept(model);
        return this;
    }
}
