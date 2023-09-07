package com.example.mobilele.exceptions.usernameChecker;


import com.example.mobilele.services.UserServiceImp;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotUsedUsernameValidator implements ConstraintValidator<NotUsedUsernameConstraint, String> {
    private final UserServiceImp service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return service.freeToUseUsername(username);
    }
}
