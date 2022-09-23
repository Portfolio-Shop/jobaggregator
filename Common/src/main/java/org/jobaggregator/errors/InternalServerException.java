package org.jobaggregator.errors;

import org.springframework.http.HttpStatus;

public class InternalServerException extends RestException {
    public InternalServerException(String message) {
        super(message, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public InternalServerException() {
        super("Internal server error", "Internal server error", HttpStatus.BAD_REQUEST);
    }
}
