package mouse.project.lib.data.exception;

import java.sql.SQLException;

public class ExecutorException extends RuntimeException{
    public ExecutorException(SQLException e) {
        super(e);
    }

    public ExecutorException(String message) {
        super(message);
    }
}
