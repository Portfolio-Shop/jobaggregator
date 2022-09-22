package org.jobaggregator.errors;

public class NotNullViolationError extends BadRequestError{
    public NotNullViolationError(String message) {
        super(message);
    }
    public NotNullViolationError(String message, String field) {
        super(message, field);
    }
    public NotNullViolationError() {
        super("Not null violation");
    }
}
