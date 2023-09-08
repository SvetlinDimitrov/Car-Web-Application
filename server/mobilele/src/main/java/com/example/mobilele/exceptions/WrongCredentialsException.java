package com.example.mobilele.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class WrongCredentialsException extends Exception {
    public WrongCredentialsException(List<ObjectError> errors) {
        super(errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n")));
    }

    public WrongCredentialsException(String message) {
        super(message);
    }
}
