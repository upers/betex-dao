package com.vareger.redis.servicies;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.vareger.models.BasketConfig;

@Service
public class RedisBasketsConfigService {
	public static final String REDIS_KEY = "basket_config_service";
	
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
	
	public List<BasketConfig> getConfigs() throws IOException {
		String configs =  valOps.get(REDIS_KEY);
		
		if ( configs != null)  {
			CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, BasketConfig.class);
			List<BasketConfig> basketsConfigs = mapper.readValue(configs, collectionType);
			
			return basketsConfigs;
		}
		
		return null;
	}
	
	public void setConfigs(List<BasketConfig> configs) throws JsonProcessingException {
		String configsStr = mapper.writeValueAsString(configs);
		valOps.set(REDIS_KEY, configsStr);
	}
}
