package com.example.mobilele.exceptions.passwordChecker;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy  = PasswordsMatchValidator.class)
@Target({ElementType.TYPE, ElementType.MODULE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatchConstrain {
    String message() default "passwords does not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
