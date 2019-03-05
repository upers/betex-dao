package com.vareger.redis.servicies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vareger.redis.models.UserSocketInfo;

/**
 * Component store user difficult subscription on basket filters update.
 * 
 * @author misha
 *
 */
@Service
public class RedisUserSubscriptionsService {

	public static final String REDIS_KEY = "redis_user_subscriptions";

	public static final String KEY_SEPARATOR = "-=-=-";

	private static final Logger log = LoggerFactory.getLogger(RedisUserSubscriptionsService.class);

	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private HashOperations<String, String, String> hashOps;

	@PostConstruct
	public void postConstructor() {
		hashOps = redisTemplate.opsForHash();
	}

	public void putSubscription(UserSocketInfo userSocketInfo) throws IOException {
		// throw exception is invalid
		checkMacAnsSession(userSocketInfo);

		String key = userSocketInfo.getServerId() + KEY_SEPARATOR + userSocketInfo.getUserSession();

		String userSocketStr = mapper.writeValueAsString(userSocketInfo);
		hashOps.put(REDIS_KEY, key, userSocketStr);
	}

	public UserSocketInfo getUserSocketInfo(String serverId, String userSession) {
		// throw exception is invalid
		checkMacAnsSession(serverId, userSession);

		String key = serverId + KEY_SEPARATOR + userSession;
		String socketInfoStr = hashOps.get(REDIS_KEY, key);

		if (socketInfoStr != null) {
			try {
				return mapper.readValue(socketInfoStr, UserSocketInfo.class);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		return null;
	}

	public void removeSocketInfo(UserSocketInfo userSocketInfo) {
		// throw exception is invalid
		checkMacAnsSession(userSocketInfo);

		String key = userSocketInfo.getServerId() + KEY_SEPARATOR + userSocketInfo.getUserSession();
		hashOps.delete(REDIS_KEY, key);
	}
	
	public List<UserSocketInfo> getAllServerUserSocketInfos(String serverId) {
		Set<String> keys = getAllServerSocketKeys(serverId);
		List<String> soketStrList = hashOps.multiGet(REDIS_KEY, keys);
		
		List<UserSocketInfo> sockets = new ArrayList<>(keys.size());
		for (String soketStr : soketStrList) {
			try {
				UserSocketInfo userSocketInfo = mapper.readValue(soketStr, UserSocketInfo.class);
				sockets.add(userSocketInfo);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return sockets;
	}
	
	private Set<String> getAllServerSocketKeys(String serverId) {
		Set<String> allKeys = hashOps.keys(REDIS_KEY);
		
		Set<String> serverKeys = new HashSet<>();
		for (String key : allKeys) {
			String[] keyParts = key.split(KEY_SEPARATOR);
			String _serverId = keyParts[0];
			log.debug("User socket redis key: " + key + " server key: " + serverId);
			if (_serverId.equals(serverId))
				serverKeys.add(key);
		}
		
		return serverKeys;
	}

	public void removeAllSubsByServerId(String serverId) {
		Set<String> keys = getAllServerSocketKeys(serverId);
		
		for (String key : keys) {
			hashOps.delete(REDIS_KEY, key);
		}
	}

	private void checkMacAnsSession(String serverMac, String userSession) {
		if (userSession == null)
			throw new IllegalAccessError("User Session info must not be NULL");
		if (serverMac == null)
			throw new IllegalAccessError("Server Session info must not be NULL");
	}

	private void checkMacAnsSession(UserSocketInfo userSocketInfo) {
		if (userSocketInfo == null)
			throw new IllegalAccessError("User socket info must not be NULL");
		String userSession = userSocketInfo.getUserSession();
		String serverMac = userSocketInfo.getServerId();
		if (userSession == null)
			throw new IllegalAccessError("User Session info must not be NULL");
		if (serverMac == null)
			throw new IllegalAccessError("Server Session info must not be NULL");
	}

}
