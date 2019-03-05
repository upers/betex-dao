package com.vareger.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class OffsetLimitValidator implements ConstraintValidator<ValidOffsetLimit, Integer> {
	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value == null)
			return false;
		
		if (value < 1)
			return false;
		
		if (value > 200)
			return false;
		
		return true;
	}

	@Override
	public void initialize(ValidOffsetLimit constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}



}
