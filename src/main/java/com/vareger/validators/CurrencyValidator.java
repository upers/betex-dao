package com.vareger.validators;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vareger.models.Currency;
import com.vareger.servicies.CurrencyService;

@Component
public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {
	
	@Autowired
	private CurrencyService service;
	
	private List<String> validCurrCodes;
	
	@PostConstruct
	private void postConstructor() {
		validCurrCodes = new ArrayList<>();
	}
	
	
	@Override
	public boolean isValid(String currencyCode, ConstraintValidatorContext ctx) {
		if (currencyCode == null)
			return false;
		
		if (validCurrCodes.contains(currencyCode))
			return true;
		
		Currency curr = service.findByCode(currencyCode);
		if (curr != null) {
			validCurrCodes.add(currencyCode);
			return true;
		}
		
		return false;
	}

	@Override
	public void initialize(ValidCurrency constraintAnnotation) {
		
	}

}
