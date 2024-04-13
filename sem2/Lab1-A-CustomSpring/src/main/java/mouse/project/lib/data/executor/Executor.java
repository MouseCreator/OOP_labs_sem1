package mouse.project.lib.data.executor;

public interface Executor {
    ExecutorResult executeQuery(String sql, Object... args);
}
