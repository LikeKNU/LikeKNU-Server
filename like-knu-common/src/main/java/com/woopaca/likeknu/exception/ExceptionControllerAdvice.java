package com.woopaca.likeknu.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<String> businessExceptionHandler(BusinessException exception) {
        String message = exception.getMessage();
        log.info("BusinessException: ", exception);
        return ResponseEntity.badRequest()
                .body(message);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<String> noResourceFoundExceptionHandler(NoResourceFoundException exception) {
        return ResponseEntity.notFound()
                .build();
    }

    @ExceptionHandler(value = ClientAbortException.class)
    protected ResponseEntity<String> clientAbortExceptionHandler(ClientAbortException exception) {
        log.error("ClientAbortException: {}", exception.getMessage());
        return ResponseEntity.ok()
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<String> exceptionHandler(Exception exception) {
        String message = exception.getMessage();
        String stackTrace = Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        log.error("Exception: ", exception);
        return ResponseEntity.internalServerError()
                .body(message);
    }
}
