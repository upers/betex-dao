package com.vareger.validators;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.vareger.config.ChartConfig;

@Component
public class RateChartIntervalValidator implements ConstraintValidator<ValidChartRateInterval, Integer> {
	private Instant minDate;
	
	private Instant maxDate;
	
	@Override
	public void initialize(ValidChartRateInterval constraintAnnotation) {
		ZonedDateTime zondeTime = ZonedDateTime.of(2017, 07, 01, 0, 0, 0, 0, ZoneId.of("UTC"));
		minDate = zondeTime.toInstant();
		
		zondeTime= ZonedDateTime.now( ZoneId.of("UTC"));
		maxDate = zondeTime.toInstant();
	}
	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		long maxInterval = maxDate.getEpochSecond() - minDate.getEpochSecond();
		if ( value > maxInterval)
			return false;
		
		for (Integer in : ChartConfig.ALLOWED_INTERVALS) {
			if (in.equals(value))
				return true;
		}
		
		return false;
	}




}
