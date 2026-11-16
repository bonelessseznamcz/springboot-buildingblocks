package com.flem.resthello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalRestControllerAdviceExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorDetails userNameNotFound(UsernameNotFoundException uex) {
        return new CustomErrorDetails(LocalDateTime.now(), "From @RestControllerAdvice", uex.getLocalizedMessage());
    }
}
