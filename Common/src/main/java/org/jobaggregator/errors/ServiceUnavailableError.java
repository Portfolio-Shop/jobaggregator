package org.jobaggregator.errors;

public class ServiceUnavailableError extends RestError {
    public ServiceUnavailableError(String service) {
        super(service + "service unavailable", 503);
    }
    public ServiceUnavailableError() {
        super("Service unavailable", 503);
    }
}
