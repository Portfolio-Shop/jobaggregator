package tech.portfolioshop.users;

import org.jobaggregator.errors.RestException;
import org.jobaggregator.errors.SerializedException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<Object> handleRestException(RestException e, WebRequest request) {
        return handleExceptionInternal(e, e.getSerializedError(), new HttpHeaders(), e.getStatusCode(), request);
    }
}
