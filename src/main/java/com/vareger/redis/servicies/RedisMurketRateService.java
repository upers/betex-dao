package com.vareger.redis.servicies;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vareger.model.wrappers.CompositeVolume;
import com.vareger.validators.CurrencyValidator;

@Service
public class RedisMurketRateService {

	private final Logger log = LoggerFactory.getLogger(RedisMurketRateService.class);

	public static final String REDIS_KEY = "overall_volume";

	@Autowired
	private CurrencyValidator currencyValidator;

	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private HashOperations<String, String, String> opsHash;

	@PostConstruct
	public void postConstructor() {
		opsHash = redisTemplate.opsForHash();
	}
	
	public void deleteAll() {
		redisTemplate.delete(REDIS_KEY);
	}

	/**
	 * Get currency rate by currency code and base currency code.<br>
	 * If no value stored in REDIS return NULL;
	 * 
	 * currCode
	 * baseCurrCode
	 */
	public CompositeVolume getCompositeVolume(String currCode, String baseCurrCode) {
		String key = currCode + baseCurrCode;
		String rateStr = opsHash.get(REDIS_KEY, key);
		CompositeVolume overallVolume = null;
		try {
			overallVolume = mapper.readValue(rateStr, CompositeVolume.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return overallVolume;
	}

	public void setCompositellVolume(CompositeVolume compositeVolume) throws JsonProcessingException {
		String currCode = compositeVolume.getCurrency();
		String baseCurrCode = compositeVolume.getBaseCurrency();

		String key = currCode + baseCurrCode;
		String overallStr = mapper.writeValueAsString(compositeVolume);

		opsHash.put(REDIS_KEY, key, overallStr);
	}

	public boolean isValid(CompositeVolume compositeVolume) {
		String baseCurrency = compositeVolume.getBaseCurrency();
		String currency = compositeVolume.getCurrency();
		
		if (!currencyValidator.isValid(currency, null))
			return false;
		if (!currencyValidator.isValid(baseCurrency, null))
			return false;
		if (compositeVolume.getMurcketsVolumes() == null)
			return false;
		if (compositeVolume.getMurcketsVolumes().isEmpty())
			return false;

		return true;
	}

}
