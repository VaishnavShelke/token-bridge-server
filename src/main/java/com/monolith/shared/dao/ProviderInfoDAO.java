package com.monolith.shared.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.entities.ChainProviderInfoEntity;
import com.monolith.tokenmint.repository.ProviderInfoRepository;

@Service
public class ProviderInfoDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ProviderInfoDAO.class);
	
	@Autowired
	private ProviderInfoRepository providerInfoRepository;
	
	public ChainProviderInfoEntity getProviderInfo(String providerId) {
		try {
			ChainProviderInfoEntity chainProviderInfoEntity = providerInfoRepository.findById(providerId).orElse(null);
			if(chainProviderInfoEntity == null) {
				logger.error("Provider Info not present for provider Id {}",providerId);
			}
			return chainProviderInfoEntity;
		} catch (Exception e) {
			logger.error("Error while fetching provider info for provider Id {} :: {}", providerId, e.getMessage());
			return null;
		}
	}
}
