package com.robustwealth.microservices.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleAllExceptions(RuntimeException ex, WebRequest req) {
        LOGGER.error("Unexpected exception thrown: {}", ex.getClass().getName(), ex);
        
        return handleExceptionInternal(ex, "{\"message\":\"" + ex.getClass().getName() + " thrown\"}",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, req);
    }
}
