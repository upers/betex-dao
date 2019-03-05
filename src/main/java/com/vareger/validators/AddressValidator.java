package com.vareger.validators;

import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class AddressValidator implements ConstraintValidator<ValidAddress, String> {

	static class AllowedLength {
		static final int hex = 40;
		static final int hexWithPrefix = 42;
	}

	@Override
	public void initialize(ValidAddress constraintAnnotation) {

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

	public static String getHexWithPrefix(String address) {
		if (address == null)
			return null;

		int length = address.length();
		if (length == AllowedLength.hexWithPrefix)
			return address;

		return "0x" + address;
	}
	
	public static String getHexClear(String address) {
		if (address == null)
			return null;

		int length = address.length();
		if (length == AllowedLength.hex)
			return address;

		return address.substring(2, address.length());
	}

}
