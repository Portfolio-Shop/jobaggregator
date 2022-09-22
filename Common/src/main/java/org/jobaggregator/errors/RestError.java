package org.jobaggregator.errors;

public abstract class RestError extends Exception{
    private final Integer statusCode;
    public RestError(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public Integer getStatusCode() {
        return statusCode;
    }
    public SerializedError getSerializedError() {
        return new SerializedError(getMessage());
    }
}
