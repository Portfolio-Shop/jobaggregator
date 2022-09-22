package org.jobaggregator.errors;

public class UnauthorizedError extends RestError {
    public UnauthorizedError(String message) {
        super(message, 401);
    }
    public UnauthorizedError() {
        super("Unauthorized access to resource", 401);
    }
}
