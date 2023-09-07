package com.example.mobilele.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String username) {
        super("User with id:" + username + " does not exists");
    }
}
