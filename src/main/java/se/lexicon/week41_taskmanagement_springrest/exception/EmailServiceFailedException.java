package se.lexicon.week41_taskmanagement_springrest.exception;

public class EmailServiceFailedException extends RuntimeException {
    public EmailServiceFailedException(String message) {
        super(message);
    }

    public EmailServiceFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
