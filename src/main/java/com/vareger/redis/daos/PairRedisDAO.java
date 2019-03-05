package com.vareger.redis.daos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vareger.models.Pair;
import com.vareger.redis.models.CurrencyPair;
import com.vareger.servicies.PairService;

@Component
public class PairRedisDAO {

	private final Logger log = LoggerFactory.getLogger(PairRedisDAO.class);

	@Autowired
	private PairService pairService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;

//	@PostConstruct
//	private void postConstructor() {
//		pushPairsToRedis();
//	}

	/**
	 * Check is input currency pair is available.<br>
	 * 
	 * cuurCode
	 * baseCurrCode
	 * @return
	 */
	public boolean isPairPresent(String cuurCode, String baseCurrCode) {
		CurrencyPair pair = getByCode(cuurCode, baseCurrCode);
		
		return (pair == null) ? false : true;
	}
	
	/**
	 * Check is input currency pair is available and return it.<br>
	 * cuurCode
	 * baseCurrCode
	 * @return
	 */
	public CurrencyPair getByCode(String cuurCode, String baseCurrCode) {
		List<CurrencyPair> currencyPairs = getPairs();
		
		for (CurrencyPair pair : currencyPairs) {
			if (pair.getCurrencyCode().equals(cuurCode) && pair.getBaseCurrencyCode().equals(baseCurrCode))
				return pair;
		}

		return null;
	}
	
	/**
	 * Check is pair active
	 * cuurCode
	 * baseCurrCode
	 * @return
	 */
	public boolean isActive(String cuurCode, String baseCurrCode) {
		CurrencyPair pair = getByCode(cuurCode, baseCurrCode);
		if (pair != null)
			return pair.getEnabled();
		
		return false;
	}

	public List<CurrencyPair> getPairs() {
		this.autoUpdateDate();

		Long listSize = redisTemplate.opsForList().size(CurrencyPair.KEY);
		List<String> pairsStr = redisTemplate.opsForList().range(CurrencyPair.KEY, 0, listSize);
		if (pairsStr != null) {
			List<CurrencyPair> currencyPairs = pairsStr.stream().map((pairStr) -> {
				try {
					return mapper.readValue(pairStr, CurrencyPair.class);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
				return null;
			}).collect(Collectors.toList());
			
			return currencyPairs;
		}

		return null;
	}

	private void autoUpdateDate() {
		if (redisTemplate.getExpire(CurrencyPair.KEY) < 0) {
			pushPairsToRedis();
		}
	}

	public void pushPairsToRedis() {
		List<CurrencyPair> pairList = new ArrayList<>();
		for (Pair p : pairService.findAll())
			pairList.add(new CurrencyPair(p));

		PairsPusher pairsUpdate = new PairsPusher(pairList);
		redisTemplate.execute(pairsUpdate);
	}

	private class PairsPusher implements SessionCallback<Void> {

		private List<String> pairListStr;

		public PairsPusher(List<CurrencyPair> currencyPairs) {
			this.pairListStr = currencyPairs.stream().map((currPair) -> {
				try {
					return mapper.writeValueAsString(currPair);
				} catch (JsonProcessingException e) {
					log.error(e.getMessage(), e);
				}

				return null;
			}).collect(Collectors.toList());
		}

		@Override
		public Void execute(RedisOperations operations) throws DataAccessException {
			try {
				while (true) {
					operations.watch(CurrencyPair.KEY);
					// start transaction
					operations.multi();
					redisTemplate.delete(CurrencyPair.KEY);
					redisTemplate.opsForList().rightPushAll(CurrencyPair.KEY, pairListStr);
					redisTemplate.boundListOps(CurrencyPair.KEY).expire(3600, TimeUnit.SECONDS);

					// execute transaction
					List<Object> list = operations.exec();
					if (list.size() > 0) {
						log.info("Pairs was updated from DB.");
						return null;
					} else
						log.warn("Can not update pairs from DB.");
				}
			} catch (Exception e) {
				try {
					operations.discard();
				} catch (Exception e1) {
					log.error(e1.getMessage(), e1);
				}
				log.error(e.getMessage(), e);
			}

			return null;
		}
	}
}
