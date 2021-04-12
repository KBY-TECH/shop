package com.team_project.shop.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class Global {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> emailNotValid(ConstraintViolationException constraintViolationException)
    {
        String msg[]=constraintViolationException.getMessage().split(":");
        return new ResponseEntity<>(msg[1],HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity ValidationError(MethodArgumentNotValidException methodArgumentNotValidException)
    {
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        return new ResponseEntity(fieldError.getDefaultMessage(),HttpStatus.NOT_ACCEPTABLE);
    }
}

