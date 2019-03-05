package com.vareger.redis.servicies;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
//import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vareger.web3j.EthNet;

@Service
public class RedisEthBlockService {
	public static final String REDIS_KEY = "ethereum_last_readed_block";

	public static final String EXCHANGE_PREFIX = "exchange";

	private final Logger log = LoggerFactory.getLogger(RedisEthBlockService.class);

	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public String getLastReadedBlock(EthNet ethNet, String appName) {
		if (ethNet == EthNet.TESTRPC)
			return null;
		String key = REDIS_KEY + appName + ethNet.name();

		return redisTemplate.opsForValue().get(key);
	}

	public void setLastReadedBlock(EthNet ethNet, String appName, BigInteger blockNum) {
		if (ethNet == EthNet.TESTRPC)
			return;

		String key = REDIS_KEY + appName + ethNet.name();

		// clear hex prefix
		redisTemplate.execute(new SessionCallback<Void>() {

			@Override
			@SuppressWarnings("unchecked")
			public Void execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
				try {

					while (true) {
						// watch for changes
						operations.watch(key);
						// start transaction
						operations.multi();

						// get previus block number
						String prevBlockNumStr = ((ValueOperations<String, String>) operations.opsForValue()).get(key);
						BigInteger prevBlockNum = (prevBlockNumStr != null) ? new BigInteger(prevBlockNumStr, 16) : BigInteger.valueOf(-1);

						log.debug("Block number: " + blockNum + " previus block number: " + prevBlockNum+ "previus block num str: " + prevBlockNumStr);
						if (blockNum.compareTo(prevBlockNum) < 1) {
							log.error("Last readed block less than previous block number: " + blockNum + "  previus block num: "
									+ prevBlockNum + "previus block num str: " + prevBlockNumStr);
							operations.discard();
							return null;
						}

						operations.opsForValue().set(key, blockNum.toString(16));

						// execute transaction
						List<Object> list = operations.exec();
						if (list.size() > 0) {
							log.info("Last readed block changed: " + blockNum.toString() + " ethNet: " + ethNet.name() + " appName: " + appName);
							break;
						} else
							log.warn("Can not change last readed block. Try later.");

					}
				} catch (Throwable e) {
					log.error(e.getMessage(), e);
					// discard transaction
					operations.discard();
				}

				return null;
			}
		});

	}

}
