package org.jobaggregator.errors;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RestException {
    public NotFoundException(String message) {
        super(message, "Not Found Error", HttpStatus.NOT_FOUND);
    }
    public NotFoundException() {
        super("Resource not found", "Not Found Error", HttpStatus.NOT_FOUND);
    }
}
