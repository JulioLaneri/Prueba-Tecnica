package com.api.msventas.controllers;

import com.api.msventas.dtos.ErrorDto;
import com.api.msventas.exceptions.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationExceptions(HttpMessageNotReadableException e) {
        String message = e.getMessage();

        return ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(message)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationExceptions(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(message)
                .build();

    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto runtimeExeptionHandler(RuntimeException e) {
        return ErrorDto.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message(e.getMessage())
                .build();

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(NotFoundException e) {
        return ErrorDto.builder()
                .code(HttpStatus.NOT_FOUND.toString())
                .message(e.getMessage())
                .build();
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(violation -> {
                    String field = violation.getPropertyPath().toString();
                    String errorMessage = violation.getMessage();
                    return  field + ": " + errorMessage;
                })
                .collect(Collectors.joining("; "));

        return ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(message)
                .build();

    }

}
