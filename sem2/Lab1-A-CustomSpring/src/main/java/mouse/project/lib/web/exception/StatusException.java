package mouse.project.lib.web.exception;

import lombok.Getter;

public class StatusException extends RuntimeException{
    @Getter
    private final int status;
    public StatusException(int status) {
        super();
        this.status = status;
    }
}
