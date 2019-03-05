package com.vareger.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vareger.models.BasketStatus;

public class BasketStatusValidator implements ConstraintValidator<ValidStatus, Integer> {
	@Override
	public void initialize(ValidStatus constraintAnnotation) {

	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		//Front have got basket can be in none status so allowed length is one more
		int maxValue = BasketStatus.values().length;
		if (value >= 0 && value <= maxValue)
			return true;
		
		return false;
	}

}
