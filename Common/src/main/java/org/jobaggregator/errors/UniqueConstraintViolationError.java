package org.jobaggregator.errors;

public class UniqueConstraintViolationError extends BadRequestError{
    public UniqueConstraintViolationError(String message) {
        super(message);
    }
    public UniqueConstraintViolationError(String message, String field) {
        super(message, field);
    }
    public UniqueConstraintViolationError() {
        super("Unique constraint violation");
    }
}
