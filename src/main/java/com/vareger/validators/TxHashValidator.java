package com.vareger.validators;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;

@Component
public class TxHashValidator implements ConstraintValidator<ValidTxHash, String> {

	static class AllowedLength {
		static final int hexWithPrefix = 66;
	}

	@Override
	public void initialize(ValidTxHash constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return false;

		int length = value.length();
		if (length == AllowedLength.hexWithPrefix) {

			String _val = (length == AllowedLength.hexWithPrefix) ? value.substring(2, value.length()) : value;
			try {
				new BigInteger(_val, 16);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}

		return true;

	}
}
