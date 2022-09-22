package org.jobaggregator.errors;

public class BadRequestError extends RestError {
    private String field;
    public BadRequestError(String message, String field) {
        super(message, 400);
        this.field = field;
    }
    public BadRequestError(String message) {
        super(message, 400);
    }
    public BadRequestError() {
        super("Bad request", 400);
    }

    @Override
    public SerializedError getSerializedError() {
        return new SerializedError(getMessage(), field);
    }
}
