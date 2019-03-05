package com.vareger.redis.servicies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.vareger.models.Rate;
import com.vareger.redis.daos.PairRedisDAO;
import com.vareger.redis.models.CurrencyPair;
import com.vareger.validators.CurrencyValidator;
import com.vareger.servicies.TickerService;

@Service
public class RedisRateService {
	
	private final Logger log = LoggerFactory.getLogger(RedisRateService.class);
	
	public static final String REDIS_KEY = "currency_rate";
	
	public static final String REDIS_EXCHANGE_KEY = "exchange_currency_rate";
	
	public static final String REDIS_KEY_PENULTIMATE = "currency_rate_penultimate";

	@Autowired
	private CurrencyValidator currencyValidator;
	
	@Autowired
	private PairRedisDAO pairRedisDAO;
	
	@Autowired
	private TickerService tickerService;
	
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
	 * Get rates by all available currency pairs.
	 * @return
	 */
	public List<Rate> getAllPairsRate() {
		List<CurrencyPair> pairs = pairRedisDAO.getPairs();
		List<Rate> rates = new ArrayList<>(pairs.size());
		for (CurrencyPair pair : pairs) {
			String currency = pair.getCurrencyCode();
			String baseCurrency = pair.getBaseCurrencyCode();
			
			Rate rate = getRate(currency, baseCurrency);
			rates.add(rate);
		}
		
		return rates;
	}
	
	/**
	 * Get currency rate by currency code and base currency code.<br>
	 * If no value stored in REDIS return NULL;
	 * currCode
	 * baseCurrCode
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Rate getRate(String currCode, String baseCurrCode) {
		String key = currCode + baseCurrCode;
		String rateStr = opsHash.get(REDIS_KEY, key);
		Rate rate = null;
		try {
			rate = mapper.readValue(rateStr, Rate.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		if (!isValidRate(rate)) {
			log.error("Rate is INVALID! Rate: " + rate);
			return null;
		}
		
		return rate;
	}
	
	public void setRate(Rate rate) throws JsonProcessingException {
		if (!isValidRate(rate)) {
			log.error("Rate is INVALID! Rate: " + rate);
			return;
		}
		
		String currCode = rate.getCurrencyCode();
		String baseCurrCode = rate.getBaseCurrencyCode();
		
		Rate penultimateRate = refreshPenultimateRate(currCode, baseCurrCode);
		rate.setPenultimateRate(penultimateRate);
		
		String key = currCode + baseCurrCode;
		String rateStr = mapper.writeValueAsString(rate);
		
		opsHash.put(REDIS_KEY, key, rateStr);
	}
	
	private Rate refreshPenultimateRate(String currCode, String baseCurrCode) throws JsonProcessingException {
		Rate rate = getRate(currCode, baseCurrCode);
		if (rate == null) {
			rate = tickerService.getPenultimateRate(currCode, baseCurrCode);
		}
		
		String key = currCode +baseCurrCode;
		String rateStr = mapper.writeValueAsString(rate);
		
		opsHash.put(REDIS_KEY_PENULTIMATE, key, rateStr);
		
		return rate;
	}
	
	public void setExchangeRate(Rate rate) throws JsonProcessingException {
		if (!isValidRate(rate)) {
			log.error("Exchange rate is INVALID! Rate: " + rate);
			return;
		}
		
		String rateStr = mapper.writeValueAsString(rate);
		
		opsHash.put(REDIS_KEY, REDIS_EXCHANGE_KEY, rateStr);
	}
	
	public Rate getExchangeRate() {
		String rateStr = opsHash.get(REDIS_KEY, REDIS_EXCHANGE_KEY);
		Rate rate = null;
		try {
			rate = mapper.readValue(rateStr, Rate.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		if (!isValidRate(rate)) {
			log.error("Rate is INVALID! Rate: " + rate);
			return null;
		}
		
		return rate;
	}
	
	private boolean isValidRate(Rate rate) {
		if (rate == null)
			return false;
		if ( !currencyValidator.isValid(rate.getCurrencyCode(), null))
			return false;
		if ( !currencyValidator.isValid(rate.getBaseCurrencyCode(), null))
			return false;
		Date date = rate.getDate();
		if (date == null)
			return false;
		
		Double price = rate.getPrice();
		if (price == null)
			return false;
		
		long rateTime = date.getTime();
		long currTime= System.currentTimeMillis();
		
		if (rateTime > currTime)
			return false;
		
		currTime -= 1000 * 60 * 60;
		
		if (currTime > rateTime)
			return false;
		
		return true;
	}
}
