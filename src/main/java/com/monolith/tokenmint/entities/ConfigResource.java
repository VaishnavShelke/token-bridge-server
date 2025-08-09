package com.monolith.tokenmint.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monolith.shared.redis.RedisDataSourceInfo;
import com.monolith.shared.utils.Utility;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "config_resource")
@IdClass(ConfigResourceId.class)
public class ConfigResource {

	private static final Logger logger = LoggerFactory.getLogger(ConfigResource.class);
	
	@Id
	@Column(name = "group_name")
	private String groupName;
	
	@Id
	@Column(name = "config_name") 
	private String name;
	
	@Column(name = "product_code")
	private String productCode;
	
	@Column(name = "config_value", columnDefinition = "TEXT")
	private String value;
	
	@Column(name = "config_type")
	private String type;
	
	@Transient
	private RedisDataSourceInfo redisDataSourceInfo = null;
	
	public RedisDataSourceInfo getParsedRedisDataSourceInfo() {
		
		if(redisDataSourceInfo != null) {
			return redisDataSourceInfo;
		}else {
			logger.info("PARSE JSON :: parsing string to RedisDataSourceInfo");
			this.redisDataSourceInfo = Utility.parseJsonToObject(value,RedisDataSourceInfo.class);
			if(redisDataSourceInfo == null) {
				logger.error("PARSE JSON :: Failed");
			}
		}
		
		return redisDataSourceInfo;
	}
}
