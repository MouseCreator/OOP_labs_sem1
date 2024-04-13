package mouse.project.lib.data.executor;

public interface Executor {
    ExecutorResult executeQuery(String sql);
    ExecutorResult executePrepared(String sql, Object... args);
}
