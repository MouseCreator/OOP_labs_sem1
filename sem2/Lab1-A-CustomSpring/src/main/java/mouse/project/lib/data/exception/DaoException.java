package mouse.project.lib.data.exception;

import java.sql.SQLException;

public class DaoException extends RuntimeException{
    public DaoException(SQLException e) {
        super(e);
    }
}
