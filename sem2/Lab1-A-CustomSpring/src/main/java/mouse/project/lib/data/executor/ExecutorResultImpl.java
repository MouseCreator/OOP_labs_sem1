package mouse.project.lib.data.executor;

import mouse.project.lib.data.orm.desc.ModelDescription;
import mouse.project.lib.data.orm.fill.ModelFill;
import mouse.project.lib.data.orm.map.OrmMap;
import mouse.project.lib.data.transformer.Transformer;

import java.sql.ResultSet;

public class ExecutorResultImpl implements ExecutorResult {

    private final ResultSet resultSet;
    private final ModelFill fill;
    private final OrmMap map;

    public ExecutorResultImpl(ResultSet resultSet, ModelFill fill, OrmMap map) {
        this.fill = fill;
        this.resultSet = resultSet;
        this.map = map;
    }

    @Override
    public ResultSet getRaw() {
        return resultSet;
    }

    @Override
    public <T> T to(Transformer<T> transformer) {
        return transformer.apply(resultSet);
    }

    @Override
    public <T> T autoTransform(Class<T> model) {
        ModelDescription<?> modelDescription = map.get(model);
        return model.cast(fill.createAndFill(resultSet, modelDescription));
    }
}
