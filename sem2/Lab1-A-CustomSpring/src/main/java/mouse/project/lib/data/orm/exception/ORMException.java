package mouse.project.lib.data.orm.exception;

public class ORMException extends RuntimeException {

    public ORMException(String s, Exception e) {
        super(s, e);
    }

    public ORMException(String s) {
        super(s);
    }
}
