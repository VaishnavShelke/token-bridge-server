package com.monolith.shared.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.monolith.tokenmint.create.controller.CreateTokenController;

public class RedisClient<Object> {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);
	
	private ValueOperations<String, Object> valueOperations;

	RedisClient(RedisTemplate<String, Object> redisTemplate) {
		this.valueOperations = redisTemplate.opsForValue();
	}
	public boolean putValue(String key, Object value) {
		try {
			valueOperations.set(key, value);
			return true;
		}catch (Exception e) {
			logger.error("!!! FATAL !!! Exception In Saving To Cache {}",e.getMessage());
			return false;
		}
	    
	}
	public Object getValue(String key) {     
		return valueOperations.get(key);
	}
	public boolean deleteValue(String key) {
	    return valueOperations.getOperations().delete(key);
	}
}
