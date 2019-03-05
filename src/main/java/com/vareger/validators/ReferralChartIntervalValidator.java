package com.vareger.validators;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class ReferralChartIntervalValidator implements ConstraintValidator<ValidChartReferralInterval, Long> {
	private Instant minDate;
	
	private Instant maxDate;
	
	private Long minValue;
	
	@Override
	public void initialize(ValidChartReferralInterval constraintAnnotation) {
		ZonedDateTime zondeTime = ZonedDateTime.of(2017, 07, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
		minDate = zondeTime.toInstant();
		
		zondeTime= ZonedDateTime.now( ZoneId.of("UTC"));
		maxDate = zondeTime.toInstant();
		
		minValue = 60l;
	}
	
	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if (value < minValue)
			return false;
		
		long maxInterval = maxDate.getEpochSecond() - minDate.getEpochSecond();
		if ( value > maxInterval)
			return false;
		
		return true;
	}




}
