package com.vareger.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	static final String ATOM = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]";
	static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)+";
	static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

	static final String PATTERN = "^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$";

	@Override
	public boolean isValid(String inEmail, ConstraintValidatorContext context) {
		if (inEmail == null)
			return false;

		
		if (!inEmail.matches(PATTERN))
			return false;
		
		return true;
	}

	@Override
	public void initialize(ValidEmail constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

}
