package com.example.mobilele.exceptions.passwordChecker;

import com.example.mobilele.domain.dtos.user.UserRegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatchConstrain, UserRegisterDto> {

    @Override
    public boolean isValid(UserRegisterDto value, ConstraintValidatorContext context) {
        return value != null &&
                value.getPassword() != null &&
                value.getConfirmPassword() != null &&
                value.getPassword().equals(value.getConfirmPassword());
    }
}
