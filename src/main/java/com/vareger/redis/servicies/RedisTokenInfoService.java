package com.vareger.redis.servicies;

import java.math.BigInteger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisTokenInfoService {

	private static final String totalSupplyKey = "betex_token_total_supply";

	private static final int radix = 16;

	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private ValueOperations<String, String> valOps;

	@PostConstruct
	public void postConstructor() {
		valOps = redisTemplate.opsForValue();
	}

	public BigInteger getTokenTotalSupply() {
		String totalSupplyStr = valOps.get(totalSupplyKey);

		return new BigInteger(totalSupplyStr, radix);
	}

	public void setTokenTotalSupply(BigInteger totalSupply) {
		if (totalSupply == null)
			throw new IllegalStateException("Total supply MUST not be NULL.");
		
		String totalSupplyStr = totalSupply.toString(radix);
		valOps.set(totalSupplyKey, totalSupplyStr);
	}
}
