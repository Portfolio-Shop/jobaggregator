package org.jobaggregator.errors;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RestException {
    public UnauthorizedException(String message) {
        super(message, "Unauthorized Error",HttpStatus.UNAUTHORIZED);
    }
    public UnauthorizedException() {
        super("Unauthorized access to resource", "Unauthorized Error",HttpStatus.UNAUTHORIZED);
    }
}
