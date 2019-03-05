package com.vareger.redis.servicies;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisCexService {
	public static final String REDIS_KEY_CEX_USER = "cex_user";
	
	public static final String REDIS_KEY_CEX_API_KEY = "cex_api_key";
	
	public static final String REDIS_KEY_CEX_API_SECRET = "cex_api_secret";
	
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
	
	public void setCexUser(@Validated @NotNull String login) {
		valOps.set(REDIS_KEY_CEX_USER, login);
	}
	
	public void setApiKey(@Validated @NotNull String apiKey) {
		valOps.set(REDIS_KEY_CEX_API_KEY, apiKey);
	}
	
	public void setApiSecret(@Validated @NotNull String apiSecret) {
		valOps.set(REDIS_KEY_CEX_API_SECRET, apiSecret);
	}
	
	public String getCexUser() {
		return valOps.get(REDIS_KEY_CEX_USER);
	}
	
	public String getCexApiKey() {
		return valOps.get(REDIS_KEY_CEX_API_KEY);
	}
	
	public String getCexApiSecret() {
		return valOps.get(REDIS_KEY_CEX_API_SECRET);
	}
}
