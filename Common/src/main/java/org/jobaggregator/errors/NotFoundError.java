package org.jobaggregator.errors;

public class NotFoundError extends RestError {
    public NotFoundError(String message) {
        super(message, 404);
    }
    public NotFoundError() {
        super("Resource not found", 404);
    }
}
