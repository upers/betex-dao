package com.vareger.redis.servicies;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vareger.validators.PrivateKeyValidator;

@Service
public class RedisEthereumService {
	
	private final Logger log = LoggerFactory.getLogger(RedisEthereumService.class);

	public static final String REDIS_KEY = "redis_contract_service";
	
	public static final String IN_ETH_NET = "in_node_rpc_url";
	
	public static final String EX_ETH_NET = "ex_node_rpc_url";

	public static final String IN_ETH_NET_WS = "in_node_rpc_url_ws";

	public static final String EX_ETH_NET_WS = "ex_node_rpc_url_ws";
	
	public static final String ORACLE_BETEX_CONTRACT_PK = "oracle_betex_contract_private_key";
	
	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private PrivateKeyValidator pkValidator;
	
	private HashOperations<String, String, String> hashOps;
	
	private ValueOperations<String, String> valOps;
	
	@PostConstruct
	public void postConstructor() {
		hashOps = redisTemplate.opsForHash();
		valOps = redisTemplate.opsForValue();
	}
	
	public JsonNode getContractAbi(String contractName) throws IOException {
		String key = contractName + "_" + "abi";
		
		String strabi = hashOps.get(REDIS_KEY, key);
		
		if ( strabi != null) 
			return mapper.readTree(strabi);
		
		return null;
	}
	
	public String getContractAddress(String contractName) {
		String key = contractName + "_" + "address";
		
		String address = hashOps.get(REDIS_KEY, key);
		
		return address;
	}
	
	public void setContractAbi(String contractName, String abi) {
		String key = contractName + "_" + "abi";
		log.debug("key: " + key + " abi: " + abi);
		if (abi == null || contractName == null)
			return;
		
		hashOps.put(REDIS_KEY, key, abi);
	}
	
	public void setContractAddress(String contractName, String address) {  	
		String key = contractName + "_" + "address";
		log.debug("key: " + key + " address: " + address);
		if (address == null || contractName == null)
			return;
		
		hashOps.put(REDIS_KEY, key, address);
	}
	
	public void setInNodeURL(String url) {
		valOps.set(IN_ETH_NET,  url);
	}
	
	public String getInNodeURL() {
		return valOps.get(IN_ETH_NET);
	}
	
	public void setExNodeURL(String url) {
		valOps.set(EX_ETH_NET,  url);
	}
	
	public String getExNodeURL() {
		return valOps.get(EX_ETH_NET);
	}

	public void setInWsNodeURL(String url) {
		valOps.set(IN_ETH_NET_WS,  url);
	}

	public String getInWsNodeURL() {
		return valOps.get(IN_ETH_NET_WS);
	}

	public void setExWsNodeURL(String url) {
		valOps.set(EX_ETH_NET_WS,  url);
	}

	public String getExWsNodeURL() {
		return valOps.get(EX_ETH_NET_WS);
	}
	
	public void setOracacleBetexPK(String pk) {
		if ( !pkValidator.isValid(pk, null)) {
			log.error("Invalid ethereum private key: " + pk);
			return;
		}
		valOps.set(ORACLE_BETEX_CONTRACT_PK, pk);
	}
	
	public String getOracleBetexPK() {
		return valOps.get(ORACLE_BETEX_CONTRACT_PK); 
	}

}
