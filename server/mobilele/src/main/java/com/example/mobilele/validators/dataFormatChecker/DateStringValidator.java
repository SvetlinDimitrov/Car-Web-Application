package com.example.mobilele.validators.dataFormatChecker;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DateStringValidator implements ConstraintValidator<ValidDateString, String> {
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && Pattern.matches(DATE_PATTERN, value)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            try {
                LocalDate parsed = LocalDate.parse(value, formatter);
                if(parsed.isAfter(LocalDate.now())){
                    context.buildConstraintViolationWithTemplate("Invalid date . DateTime cannot be in the future.")
                            .addConstraintViolation();
                    return false;
                }
                return true;
            }catch (DateTimeException e){
                context.buildConstraintViolationWithTemplate(e.getMessage())
                        .addConstraintViolation();
                return false;
            }
        }
        context.buildConstraintViolationWithTemplate("Invalid date format. Please use 'yyyy-MM-dd' format.")
                .addConstraintViolation();
        return false;
    }
}
