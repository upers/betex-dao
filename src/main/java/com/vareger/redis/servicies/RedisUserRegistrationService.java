package com.vareger.redis.servicies;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vareger.models.User;
import com.vareger.validators.AddressValidator;
import com.vareger.validators.EmailValidator;

@Service
public class RedisUserRegistrationService {

	private static final Logger log = LoggerFactory.getLogger(RedisUserSubscriptionsService.class);

	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;

	@Autowired
	private AddressValidator addressValidator;

	@Autowired
	private EmailValidator emailValidatr;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private ValueOperations<String, String> valOps;

	@PostConstruct
	private void postConstructor() {
		valOps = redisTemplate.opsForValue();
	}

	/**
	 * Save user to redis with expired 24 hour.<br>
	 * Key is a md5 hash of ethereum address.
	 * 
	 * user
	 * @return md5 hash of user ethereum address
	 */
	public String save(User user) {
		valid(user);
		String clearHex = AddressValidator.getHexClear(user.getAddress());
		String md5Hash = DigestUtils.md5Hex(clearHex);
		
		return save(user, md5Hash);
	}
	
	/**
	 * Save user to redis with expired 24 hour.<br>
	 * Key is a md5 hash of email address.
	 * 
	 * user
	 * @return md5 hash of user ethereum address
	 */
	public String saveByEmail(User user) {
		valid(user);
		String md5Hash = DigestUtils.md5Hex(user.getEmail());
		
		return save(user, md5Hash); 
	}
	
	private String save(User user, String key) {
		try {
			String userStr = mapper.writeValueAsString(user);
			valOps.set(key, userStr, 1, TimeUnit.DAYS);

			return key;
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	public User get(String key) {
		String userStr = valOps.get(key);
		if (userStr == null)
			return null;
		try {
			User user = mapper.readValue(userStr, User.class);

			return user;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}

	private void valid(User user) {
		if (user == null)
			throw new IllegalStateException("User is null.");

		String address = user.getAddress();
		boolean isAddressValid = addressValidator.isValid(address, null);
		if (!isAddressValid)
			throw new IllegalStateException("User address is not valid");

		String email = user.getEmail();
		boolean isEmailValid = emailValidatr.isValid(email, null);
		if (!isEmailValid)
			throw new IllegalStateException("User email is not valid");

	}

}
