package com.example.mobilele.exceptions.usernameChecker;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy  = NotUsedUsernameValidator.class)
@Target({ElementType.METHOD , ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotUsedUsernameConstraint {
    String message() default "email already exits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
