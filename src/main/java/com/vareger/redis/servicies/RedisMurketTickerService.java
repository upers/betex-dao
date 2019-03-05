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
import com.vareger.models.Ticker;
import com.vareger.validators.CurrencyValidator;

@Service
public class RedisMurketTickerService {

	public static final String REDIS_KEY = "last_murket_ticker";
	
	private final Logger log = LoggerFactory.getLogger(RedisMurketTickerService.class);

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
	
	/**
	 * Get
	 * currCode
	 * baseCurrCode
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Ticker getTicker(Integer murketId, String currCode, String baseCurrCode) {
		String key = murketId + currCode + baseCurrCode;
		String tickerStr = opsHash.get(REDIS_KEY, key);
		Ticker ticker = null;
		try {
			ticker = mapper.readValue(tickerStr, Ticker.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return ticker;
	}
	
	public void setTicker(Ticker ticker) throws JsonProcessingException {
		if (!isValidRate(ticker)) {
			log.error("Ticker is INVALID! Ticker: " + ticker);
			return;
		}
			
		String key = ticker.getMurket().getId() + ticker.getCurrency().getCode() + ticker.getBaseCurrency().getCode();
		String tickerStr = mapper.writeValueAsString(ticker);
		
		opsHash.put(REDIS_KEY, key, tickerStr);
	}
	
	private boolean isValidRate(Ticker ticker) {
		if (ticker == null)
			return false;
		if (ticker.getMurket() == null)
			return false;
		if (ticker.getMurket().getId() == null)
			return false;
		if (ticker.getCurrency() == null)
			return false;
		if (ticker.getBaseCurrency() == null)
			return false;
		if ( !currencyValidator.isValid(ticker.getCurrency().getCode(), null))
			return false;
		if ( !currencyValidator.isValid(ticker.getBaseCurrency().getCode(), null))
			return false;
		
		return true;
	}
}
