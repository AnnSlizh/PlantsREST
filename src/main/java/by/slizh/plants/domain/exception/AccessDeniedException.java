package by.slizh.plants.domain.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super();
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}