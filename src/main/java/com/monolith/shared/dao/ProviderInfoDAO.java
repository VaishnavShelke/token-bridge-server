package com.monolith.shared.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.beans.ProviderInfo;

@Service
public class ProviderInfoDAO {
	

	private static final Logger logger = LoggerFactory.getLogger(ProviderInfoDAO.class);
	
	@Autowired
	@Qualifier("tokenmintjdbctemplate")
	JdbcTemplate globalJdbcTemplate;
	
	public ProviderInfo getProviderInfo(String providerId) {
		String query = "SELECT * FROM chain_provider_info WHERE provider_id=?";
		List<ProviderInfo> providerInfoList= globalJdbcTemplate.query(query, new BeanPropertyRowMapper<ProviderInfo>(ProviderInfo.class),providerId);
		if(providerInfoList == null || providerInfoList.size()==0) {
			logger.error("Provider Info not present for provider Id {}",providerId);
			return null;
		}else {
			return providerInfoList.get(0);
		}
	}
}
