package com.vareger.validators;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class ToDateValidator implements ConstraintValidator<ValidToDate, Date> {
	
	private Instant minDate;

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		if (value == null)
			value = new Date();
		
		Instant inDate = value.toInstant();
		
		if (inDate.isBefore(minDate))
			return false;
		
		ZonedDateTime zondeTime= ZonedDateTime.now( ZoneId.of("UTC"));
		Instant maxDate = zondeTime.toInstant();
		
		if (inDate.isAfter(maxDate))
			return false;
		
		return true;
	}

	@Override
	public void initialize(ValidToDate constraintAnnotation) {
		ZonedDateTime zondeTime= ZonedDateTime.of(2015, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
		minDate = zondeTime.toInstant();
	}



}
