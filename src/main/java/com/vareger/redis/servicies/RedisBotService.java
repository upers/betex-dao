package com.vareger.redis.servicies;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.vareger.validators.AddressValidator;

@Service
public class RedisBotService {
	public static final String REDIS_KEY = "bot_addresses";
	
	public static final String REDIS_SPONSOR_KEY = "sponsor_addresses";

	public static final String REDIS_BOT_SPONSOR_KEY = "bot_sponsor_addresses";
	
	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private AddressValidator addressValidator;
	
	private ValueOperations<String, String> valOps;
	
	@PostConstruct
	public void postConstructor() {
		valOps = redisTemplate.opsForValue();
	}
	
	public List<String> getBotAddresses() throws IOException {
		String addressesStr =  valOps.get(REDIS_KEY);
		
		if ( addressesStr != null)  {
			CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, String.class);
			List<String> addresses = mapper.readValue(addressesStr, collectionType);
			
			return addresses;
		}
		
		return null;
	}
	
	public void setBotAddresses(List<String> addresses) throws JsonProcessingException {
		valid(addresses);
		List<String> clearAddresses = addresses.stream().map(AddressValidator::getHexClear).collect(Collectors.toList());
		String addressesStr = mapper.writeValueAsString(clearAddresses);
		valOps.set(REDIS_KEY, addressesStr);
	}

	public List<String> getSponsorAddresses() throws IOException {
		String addressesStr =  valOps.get(REDIS_SPONSOR_KEY);
		
		if ( addressesStr != null)  {
			CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, String.class);
			List<String> addresses = mapper.readValue(addressesStr, collectionType);
			
			return addresses;
		}
		
		return null;
	}
	
	public void setSponsorAddresses(List<String> addresses) throws JsonProcessingException {
		valid(addresses);
		List<String> clearAddresses = addresses.stream().map(AddressValidator::getHexClear).collect(Collectors.toList());
		String addressesStr = mapper.writeValueAsString(clearAddresses);
		valOps.set(REDIS_SPONSOR_KEY, addressesStr);
	}
	
	public void valid(List<String> addresses) {
		for (String address :addresses) {
			boolean isValid = addressValidator.isValid(address, null);
			if (!isValid)
				throw new IllegalStateException("Bot address is invalid. Address: " + address);
		}
	}
}
