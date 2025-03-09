package com.monolith.shared.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.monolith.shared.redis.RedisDataSourceInfo;
import com.monolith.shared.utils.GameServerConstants.GameServerEndpoint;
import com.monolith.shared.utils.Utility;

import lombok.Data;

@Service
public class ConfigResourceDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigResourceDAO.class);
	
	@Autowired
	@Qualifier("tokenmintjdbctemplate")
	JdbcTemplate globalJdbcTemplate;
	
	public RedisDataSourceInfo getRedisConfigByNameAndProduct(String groupName, String name) {
		
		String query = "SELECT * FROM config_resource WHERE GROUP_NAME=? AND CONFIG_NAME=?";
		try {
			List<ConfigResourceEntity> configResourceList= globalJdbcTemplate.query(query, new BeanPropertyRowMapper<ConfigResourceEntity>(ConfigResourceEntity.class),groupName,name);
			if(configResourceList == null || configResourceList.size()==0) {
				logger.error("No Such Config Resource Found");
				return null;
			}else {
				return configResourceList.get(0).getParsedRedisDataSourceInfo();
			}
		}catch (Exception e) {
			logger.error("Error Whil fetching ConfigResource {} for group {} {}",name,groupName,e.getMessage());
		}
		return null;
	}

}

@Data
class ConfigResourceEntity{

	private static final Logger logger = LoggerFactory.getLogger(ConfigResourceEntity.class);
	
	private String groupName;
	private String name;
	private String productCode;
	private String value;
	private String type;
	
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
