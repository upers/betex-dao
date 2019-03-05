package com.vareger.redis.servicies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vareger.redis.models.BrokerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisBrokerInfoService {
    public static final String REDIS_BROKER_DEBT_KEY = "redis_broker_debt_key";

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

    public void setBrokerInfo(BrokerInfo brokerInfo) throws JsonProcessingException {
        Integer id = brokerInfo.getId();
        if (id == null)
            throw new NullPointerException("Broker id is null");

        String value = mapper.writeValueAsString(brokerInfo);

        opsHash.put(REDIS_BROKER_DEBT_KEY, id.toString(), value);
    }

    public BrokerInfo getBrokerInfo(Integer id) throws IOException {
        String value = opsHash.get(REDIS_BROKER_DEBT_KEY, id.toString());
        if (value == null)
            return null;

        return mapper.readValue(value, BrokerInfo.class);
    }

    public List<BrokerInfo> getAllBrokersInfo() throws IOException {
        Map<String, String> entries = opsHash.entries(REDIS_BROKER_DEBT_KEY);
        List<BrokerInfo> result = new ArrayList<>(entries.size());
        for (String val : entries.values()) {
            BrokerInfo brokerInfo = mapper.readValue(val, BrokerInfo.class);
            result.add(brokerInfo);
        }

        return result;
    }

}
