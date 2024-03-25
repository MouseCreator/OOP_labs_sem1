package mouse.project.lib.data.transaction;

public interface TransactionManager {
    Transaction begin();
    void rollback(Transaction transaction);
    void commit(Transaction transaction);
}
