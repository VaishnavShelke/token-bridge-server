package com.monolith.shared.dao;

import com.monolith.tokenmint.entities.ConfigResourceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.shared.redis.RedisDataSourceInfo;
import com.monolith.tokenmint.repository.ConfigResourceRepository;

@Service
public class ConfigResourceDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigResourceDAO.class);
	
	@Autowired
	private ConfigResourceRepository configResourceRepository;
	
	public RedisDataSourceInfo getRedisConfigByNameAndProduct(String groupName, String name) {
		try {
			ConfigResourceEntity configResourceEntity = configResourceRepository.findByGroupNameAndName(groupName, name).orElse(null);
			if(configResourceEntity == null) {
				logger.error("No Such Config Resource Found");
				return null;
			}else {
				return configResourceEntity.getParsedRedisDataSourceInfo();
			}
		}catch (Exception e) {
			logger.error("Error While fetching ConfigResourceEntity {} for group {} {}",name,groupName,e.getMessage());
			return null;
		}
	}

}
