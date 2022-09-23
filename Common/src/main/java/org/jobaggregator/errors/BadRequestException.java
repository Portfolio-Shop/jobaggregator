package org.jobaggregator.errors;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RestException {
    private String field;
    public BadRequestException(String message, String errorName, String field) {
        super(message, errorName, HttpStatus.BAD_REQUEST);
        this.field = field;
    }
    public BadRequestException(String message, String field) {
        super(message, "Bad request Error", HttpStatus.BAD_REQUEST);
        this.field = field;
    }
    public BadRequestException(String message) {
        super(message,"Bad request Error", HttpStatus.BAD_REQUEST);
    }
    public BadRequestException() {
        super("Bad request", "Bad request Error", HttpStatus.BAD_REQUEST);
    }

    @Override
    public SerializedException getSerializedError() {
        return new SerializedException(getMessage(), getName(), field);
    }
}
