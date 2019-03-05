package com.vareger.validators;

import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class PrivateKeyValidator implements ConstraintValidator<ValidPrivateKey, String> {

	static class AllowedLength {
		static final int hex = 64;
		static final int hexWithPrefix = 66;
	}

	@Override
	public void initialize(ValidPrivateKey constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return false;

		int length = value.length();
		if (length == AllowedLength.hex || length == AllowedLength.hexWithPrefix) {

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

	public static String getHexWithPrefix(String hex) {
		if (hex == null)
			return null;

		int length = hex.length();
		if (length == AllowedLength.hexWithPrefix)
			return hex;

		return "0x" + hex;
	}
	
	public static String getHexClear(String hex) {
		if (hex == null)
			return null;

		int length = hex.length();
		if (length == AllowedLength.hex)
			return hex;

		return hex.substring(2, hex.length());
	}

}
