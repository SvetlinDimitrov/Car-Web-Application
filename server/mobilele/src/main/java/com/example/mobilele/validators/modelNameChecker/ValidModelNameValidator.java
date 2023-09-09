package com.example.mobilele.validators.modelNameChecker;

import com.example.mobilele.services.ModelServiceImp;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidModelNameValidator implements ConstraintValidator<ValidModelName, String> {

    private final ModelServiceImp modelServiceImp;

    @Override
    public boolean isValid(String brandName, ConstraintValidatorContext context) {
       return !modelServiceImp.findByName(brandName);
    }
}
