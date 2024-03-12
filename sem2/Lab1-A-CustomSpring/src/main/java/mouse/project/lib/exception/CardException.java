package mouse.project.lib.exception;

public class CardException extends RuntimeException{
    public CardException() {
        super();
    }

    public CardException(String message) {
        super(message);
    }

    public CardException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardException(Throwable cause) {
        super(cause);
    }

    protected CardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
