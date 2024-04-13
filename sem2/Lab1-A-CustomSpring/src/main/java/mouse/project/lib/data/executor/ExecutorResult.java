package mouse.project.lib.data.executor;

import mouse.project.lib.data.transformer.Transformer;

import java.sql.ResultSet;

public interface ExecutorResult {
    ResultSet getRaw();
    <T> T to(Transformer<T> transformer);
    <T> T autoTransform(Class<T> model);
}
