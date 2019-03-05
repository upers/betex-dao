package com.vareger.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vareger.redis.daos.PairRedisDAO;
import com.vareger.redis.models.CurrencyPair;

@Component
public class CurrencyPairValidator implements ConstraintValidator<ValidCurrencyPair, String> {
	
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(CurrencyPairValidator.class);
	
	@Autowired
	private PairRedisDAO pairRedisDAO;
	
	@Override
	public boolean isValid(String pathPair, ConstraintValidatorContext ctx) {
		
		CurrencyPair pairRedis = CurrencyPair.pathToPair(pathPair);
		
		if (pairRedis == null)
			return false;
			
		return pairRedisDAO.isPairPresent(pairRedis.getCurrencyCode(), pairRedis.getBaseCurrencyCode());
	}
	
	
	@Override
	public void initialize(ValidCurrencyPair constraintAnnotation) {
	}

}
