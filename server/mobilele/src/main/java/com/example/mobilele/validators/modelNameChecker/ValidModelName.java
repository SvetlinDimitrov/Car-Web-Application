package com.example.mobilele.validators.modelNameChecker;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidModelNameValidator.class)
public @interface ValidModelName {
    String message() default "This name already exits in the date base.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
