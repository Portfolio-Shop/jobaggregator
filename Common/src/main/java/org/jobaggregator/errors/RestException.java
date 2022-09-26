package org.jobaggregator.errors;

import org.springframework.http.HttpStatus;

public abstract class RestException extends Exception{
    private final HttpStatus statusCode;
    private final String name;
    public RestException(String message, String name, HttpStatus statusCode) {
        super(message);
        this.name = name;
        this.statusCode = statusCode;
    }
    public HttpStatus getStatusCode() {
        return statusCode;
    }
    public String getName() {
        return name;
    }
    public SerializedException getSerializedError() {
        return new SerializedException(getMessage(), getName());
    }
}
