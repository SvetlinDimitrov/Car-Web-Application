package com.example.mobilele.exceptions.passwordChecker;

import com.example.mobilele.domain.dtos.user.UserBaseDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatchConstrain, UserBaseDto> {

    @Override
    public boolean isValid(UserBaseDto value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getConfirmPassword());
    }
}
