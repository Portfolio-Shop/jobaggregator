package tech.portfolioshop.users;

import org.jobaggregator.errors.RestException;
import org.jobaggregator.errors.SerializedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<String> handleRestException(RestException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getSerializedError().toString());
    }
}
