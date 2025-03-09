package com.tokenmint.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.monolith.MonolithApplication;
import com.monolith.shared.redis.RedisClient;
import com.monolith.shared.redis.RedisClientFactory;

@SpringBootTest
@ContextConfiguration(classes = MonolithApplication.class)
public class ConfigTest01 {

	@Autowired
	RedisClientFactory redisClientFactory;
	
	@Test
	public void testMain() {
		try {
			RedisClient<Object> rc = redisClientFactory.getRedisClientForEntity("TokenMint");
			String str = new String("PRACTISE STRING VALUE");
			rc.putValue("PRACTISE", str);
			System.out.println(rc.getValue("PRACTISE"));
			
			RedisClient<Object> rk = redisClientFactory.getRedisClientForEntity("TokenMint");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
