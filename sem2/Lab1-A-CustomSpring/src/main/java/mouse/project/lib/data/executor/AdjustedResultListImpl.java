package mouse.project.lib.data.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AdjustedResultListImpl<T> implements AdjustedResultList<T> {

    private final List<T> models;

    public AdjustedResultListImpl(List<T> models) {
        this.models = models;
    }

    @Override
    public AdjustedResultList<T> apply(Consumer<T> consumer) {
        models.forEach(consumer);
        return this;
    }

    @Override
    public List<T> get() {
        return new ArrayList<>(models);
    }
}
