package com.freetech.sample.securityqueryservice.application.exceptions;

import exceptions.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceException {
    @ExceptionHandler(value = BussinessException.class)
    public ResponseEntity<Error> handlerBussinessException(BussinessException ex) {
        var error = Error.builder().code(ex.getCode()).message(ex.getMessage()).cause(ex.getMessageCause()).build();
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }
}
