package com.example.mobilele.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class WrongCredentialsException extends Exception {
    private final List<String> messages = new ArrayList<>();

    public WrongCredentialsException(List<ObjectError> errors) {
        messages.addAll(errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList());

    }

    public WrongCredentialsException(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages.stream()
                .filter(m -> !m.isBlank())
                .toList();
    }
}
