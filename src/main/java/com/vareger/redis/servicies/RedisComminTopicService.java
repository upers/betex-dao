package com.vareger.redis.servicies;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

/**
 * Component store user difficult subscription on basket filters update.
 * 
 * @author misha
 *
 */
@Service
public class RedisComminTopicService {

	public static final String REDIS_KEY = "redis_common_topic";

	public static final String REDIS_KEY_AMOUNT = "redis_common_topic_amount";

	private static final Logger log = LoggerFactory.getLogger(RedisComminTopicService.class);

	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper mapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private ValueOperations<String, String> ops;
	
//	private HashOperations<String, String, String> hashOps;

	@PostConstruct
	public void postConstructor() {
		ops = redisTemplate.opsForValue();
//		hashOps = redisTemplate.opsForHash();
	}

	public Set<String> getTopics() throws IOException {
		String topicsStr = ops.get(REDIS_KEY);
		if (topicsStr != null) {
			CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(Set.class, String.class);
			Set<String> topics = mapper.readValue(topicsStr, collectionType);

			return topics;
		}

		return null;
	}

	public void addTopic(String topic) {
		TopicController topicController = new TopicController(topic, TopicOperation.ADD);
		redisTemplate.execute(topicController);
	}

	public void removeTopic(String topic) {
		TopicController topicController = new TopicController(topic, TopicOperation.REMOVE);
		redisTemplate.execute(topicController);
	}

	private enum TopicOperation {
		ADD, REMOVE;
	}

	private class TopicController implements SessionCallback<String> {

		private String topic;

		private TopicOperation oper;
		

		public TopicController(String topic, TopicOperation oper) {
			this.topic = topic;
			this.oper = oper;
		}

		@Override
		@SuppressWarnings({ "unchecked" })
		public String execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
			String result = null;
			try {
				if (topic == null || topic.equals(""))
					return null;
				
				log.info("Try add some thing 2");
				while (true) {
					// watch for changes
					operations.watch(Arrays.asList(REDIS_KEY, REDIS_KEY_AMOUNT));

					// get baskets from redis
					String topicsStr = ((ValueOperations<String, String>)operations.opsForValue()).get(REDIS_KEY);
					String amountStr = ((HashOperations<String, String, String>)operations.opsForHash()).get(REDIS_KEY_AMOUNT, topic);
					
					// start transaction
					operations.multi();

					Set<String> topics = null;
					if (topicsStr != null) {
						CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(Set.class, String.class);
						topics = mapper.readValue(topicsStr, collectionType);
					} else {
						topics = new HashSet<>();
					}

					Integer amount = null;
					if (amountStr != null) {
						amount = Integer.valueOf(amountStr);
						log.debug("Amount got from redis: " + amount);
					}
					else
						amount = 0;
					
					boolean wasRemoved = false;
					if (oper == TopicOperation.ADD) {
						topics.add(topic);
						amount += 1;
						log.debug("On topic: " + topic + " subscribed: " + amount + " users.");
					} else {
						amount = (amount > 0) ? amount - 1: amount;
						log.debug("On topic: " + topic + " subscribed: " + amount + " users.");
						if (amount <= 0)
							wasRemoved = topics.remove(topic);
					}

					topicsStr = mapper.writeValueAsString(topics);
					((ValueOperations<String, String>)operations.opsForValue()).set(REDIS_KEY, topicsStr);
					((ValueOperations<String, String>)operations.opsForValue()).get(REDIS_KEY);
					if (!wasRemoved)
						((HashOperations<String, String, String>)operations.opsForHash()).put(REDIS_KEY_AMOUNT, topic, amount.toString());
					else
						((HashOperations<String, String, String>)operations.opsForHash()).delete(REDIS_KEY_AMOUNT, topic);
					
					List<Object> list = operations.exec();
					if (list.size() > 1) {
						if (oper == TopicOperation.ADD) {
							log.debug("Store common topic in redis was successfully , topic: " + topic);
							log.debug("All topics: " + topicsStr);
						} else
							log.debug("Remove common topic from redis was successfully , topic: " + topic);
						break;
					} else {
						log.warn("Can not add/remove topic. Try later.");
						operations.discard();
					}

				}
			} catch (Exception e) {
				// discard transaction
				operations.discard();
				log.error("Can not add/remove topick. Try later");
			}

			return result;
		}

	}

}
