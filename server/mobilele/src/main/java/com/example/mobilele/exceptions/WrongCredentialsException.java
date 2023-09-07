package com.example.mobilele.exceptions;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class WrongCredentialsException extends Exception {
    public WrongCredentialsException(List<FieldError> errors) {
        super(errors.stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.joining("\n")));
    }

    public WrongCredentialsException(String message) {
        super(message);
    }
}
