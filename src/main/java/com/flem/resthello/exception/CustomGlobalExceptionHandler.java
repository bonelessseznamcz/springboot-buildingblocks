package com.flem.resthello.exception;

import jakarta.validation.ConstraintViolationException;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

//@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        CustomErrorDetails details = new CustomErrorDetails(LocalDateTime.now(),
                "Argument not valid exception",
                ex.getMessage());

        return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        CustomErrorDetails details = new CustomErrorDetails(LocalDateTime.now(),
                "Method not allowed exception",
                ex.getMessage());

        return new ResponseEntity<>(details,HttpStatus.METHOD_NOT_ALLOWED);
    }

    // custom exception
    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException uex, WebRequest request) {
        CustomErrorDetails details = new CustomErrorDetails(LocalDateTime.now(),
                uex.getLocalizedMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(details,HttpStatus.NOT_FOUND);
    }

    // custom exception
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException uex, WebRequest request) {
        CustomErrorDetails details = new CustomErrorDetails(LocalDateTime.now(),
                uex.getLocalizedMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
    }
}
