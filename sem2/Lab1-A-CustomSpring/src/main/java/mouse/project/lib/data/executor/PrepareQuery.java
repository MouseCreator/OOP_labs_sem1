package mouse.project.lib.data.executor;

public interface PrepareQuery {
    PrepareQuery provide(String id, Object value);
    SingleQueryExecutor finish();
}
