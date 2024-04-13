package mouse.project.lib.data.executor;

import mouse.project.lib.data.transformer.Transformer;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface ExecutorResult {
    ResultSet getRaw();
    <T> T to(Transformer<T> transformer);
    <T> T model(Class<T> model);
    <T> List<T> list(Class<T> model);
    <T> Optional<T> optional(Class<T> model);
    <T> AdjustedResult<T> adjusted(Class<?> model);
}
