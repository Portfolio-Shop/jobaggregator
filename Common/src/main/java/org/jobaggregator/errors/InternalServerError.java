package org.jobaggregator.errors;

public class InternalServerError extends RestError {
    public InternalServerError(String message) {
        super(message, 500);
    }
    public InternalServerError() {
        super("Internal server error", 500);
    }
}
