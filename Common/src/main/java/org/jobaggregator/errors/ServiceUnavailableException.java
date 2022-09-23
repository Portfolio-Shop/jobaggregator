package org.jobaggregator.errors;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends RestException {
    public ServiceUnavailableException(String service, String errorName) {
        super(service + "service unavailable", errorName, HttpStatus.SERVICE_UNAVAILABLE);
    }
    public ServiceUnavailableException(String service) {
        super(service + "service unavailable", "Service Unavailable Error", HttpStatus.SERVICE_UNAVAILABLE);
    }
    public ServiceUnavailableException() {
        super("Service unavailable", "Service Unavailable Error", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
