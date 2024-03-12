package mouse.project.lib.exception;

public class PreparedActionException extends RuntimeException{
    public PreparedActionException() {
        super();
    }

    public PreparedActionException(String message) {
        super(message);
    }

    public PreparedActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreparedActionException(Throwable cause) {
        super(cause);
    }

    protected PreparedActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
