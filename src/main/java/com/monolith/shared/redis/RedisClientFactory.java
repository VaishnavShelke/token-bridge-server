package com.monolith.shared.redis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.monolith.shared.dao.ConfigResourceDAO;

import io.lettuce.core.ReadFrom;
import jakarta.annotation.PostConstruct;

@Service 
public class RedisClientFactory {
	private static final Logger logger = LoggerFactory.getLogger(RedisClientFactory.class);
	
	public static final String ENTITY_TOKEN_MINT = "TokenMint";
	
	@Autowired
	ConfigResourceDAO configResourceDAO;
	
	private Map<String,RedisClient<Object>> redisClientMap = null;
	
	@PostConstruct
	private void init() {
		redisClientMap = new HashMap<>();
	}
	
	public RedisClient<Object> getRedisClientForEntity(String entity){
		
		RedisDataSourceInfo redisDataSourceInfo = configResourceDAO.getRedisConfigByNameAndProduct(entity,"REDIS_CONFIG");
		
		if(redisClientMap.get(entity) == null) {
			try {
				logger.info("INITIALIZING REDIS CLIENT :: for entity {}",entity);
				RedisConfiguration  redisClusterConfiguration = redisConfiguration(redisDataSourceInfo);
				LettuceConnectionFactory redLettuceConnectionFactory = redisConnectionFactory(redisClusterConfiguration);
				RedisTemplate<String, Object> redisTemplate = redisTemplate(redLettuceConnectionFactory);
				RedisClient<Object> rclient = (new RedisClient<Object>(redisTemplate));
				redisClientMap.put(entity, rclient);
			}catch (Exception e) {
				logger.error("Could Not Make Redis Client for entity {} :: error {}",entity,e.getMessage());
			}
		}
		return redisClientMap.get(entity);
	}

/**
 * ....Cluster Configuration is Disabled for redis...
	private RedisClusterConfiguration redisConfiguration(RedisDataSourceInfo redisDataSourceInfo) {
	    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisDataSourceInfo.getClusterNodes());
	    redisClusterConfiguration.setMaxRedirects(Integer.parseInt(redisDataSourceInfo.getMaxRedirects()));
	    redisClusterConfiguration.setPassword(redisDataSourceInfo.getPassword());
	    return redisClusterConfiguration;
	}
*/
	
	private RedisStandaloneConfiguration redisConfiguration(RedisDataSourceInfo redisDataSourceInfo) {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisDataSourceInfo.getHostname());
		redisStandaloneConfiguration.setPort(Integer.valueOf(redisDataSourceInfo.getPort()));
//		redisStandaloneConfiguration.setPassword(redisDataSourceInfo.getPassword());
	    return redisStandaloneConfiguration;
	}
	
	private LettuceConnectionFactory redisConnectionFactory(RedisConfiguration redisConfiguration) {
		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
	            .readFrom(ReadFrom.REPLICA_PREFERRED).build();
	    LettuceConnectionFactory lcf = new LettuceConnectionFactory(redisConfiguration, clientConfig);
	    lcf.afterPropertiesSet();
	    return lcf;
	}
	
	private RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
	    template.afterPropertiesSet();
	    return template;
	}
}
