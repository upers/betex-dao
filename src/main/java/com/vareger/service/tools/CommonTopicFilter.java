package com.vareger.service.tools;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vareger.config.CommonTopicPrefixes;
import com.vareger.redis.servicies.RedisComminTopicService;

@Component
public class CommonTopicFilter {
	
	private static final Logger log = LoggerFactory.getLogger(CommonTopicFilter.class);
	
	@Autowired
	private RedisComminTopicService redisComminTopicService;
	
	public void checkAndAdd(String topic) {
		if (topic == null || topic.equals("")) {
			log.warn("Try check and add empty topic: " + topic);
			return;
		}
		
		if (topic.startsWith(CommonTopicPrefixes.BasketFilter.destenition)) {
			redisComminTopicService.addTopic(topic);
			log.debug("Topic: " + topic + " was added to Redis as common topic.");
		}
	}
	
	public void checkAndRemove(String topic) {
		if (topic == null || topic.equals("")) {
			log.warn("Try check and remove empty topic: " + topic);
			return;
		}
		
		if (topic.startsWith(CommonTopicPrefixes.BasketFilter.destenition)) {
			redisComminTopicService.removeTopic(topic);
			log.debug("Topic: " + topic + " was removed from Redis common topics.");
		}
	}
	
	public void checkAndRemove(Collection<String> topics) {
		if (topics == null) {
			log.warn("Try check and remove topics: " + topics);
			return;
		}
		
		for (String topic : topics) {
			if (topic == null || topic.equals(""))
				continue;
			
			if (topic.startsWith(CommonTopicPrefixes.BasketFilter.destenition)) {
				redisComminTopicService.removeTopic(topic);
				log.debug("Topic: " + topic + " was removed from Redis common topics.");
			}
		}
			
	}
	
}
