package com.monolith.shared.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.shared.redis.RedisDataSourceInfo;
import com.monolith.tokenmint.entities.ConfigResource;
import com.monolith.tokenmint.repository.ConfigResourceRepository;

@Service
public class ConfigResourceDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigResourceDAO.class);
	
	@Autowired
	private ConfigResourceRepository configResourceRepository;
	
	public RedisDataSourceInfo getRedisConfigByNameAndProduct(String groupName, String name) {
		try {
			ConfigResource configResource = configResourceRepository.findByGroupNameAndName(groupName, name).orElse(null);
			if(configResource == null) {
				logger.error("No Such Config Resource Found");
				return null;
			}else {
				return configResource.getParsedRedisDataSourceInfo();
			}
		}catch (Exception e) {
			logger.error("Error While fetching ConfigResource {} for group {} {}",name,groupName,e.getMessage());
			return null;
		}
	}

}
