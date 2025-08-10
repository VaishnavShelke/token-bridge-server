package com.monolith.shared.dao;

import com.monolith.tokenmint.entities.ETHContractInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.repository.ETHContractInfoRepository;


@Service
public class EthContractInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(EthContractInfoDAO.class);

	@Autowired
	private ETHContractInfoRepository ethContractInfoRepository;
	

	public ETHContractInfoEntity getEthContractInfoForGame(String gameId, String ethContractId) {
		try {
			return ethContractInfoRepository.findByGameIdAndEthContractId(gameId, ethContractId).orElse(null);
		}catch (Exception e) {
			logger.error("Error While fetching EthContract Info for game {} and Contract id {} error :: {}",gameId,ethContractId,e.getMessage());
			return null;
		}
	}

	public boolean insertEthContractInfo(ETHContractInfoEntity ethContractInfoEntity) {
		try {
			ethContractInfoRepository.save(ethContractInfoEntity);
			logger.info("Successfully saved ETH contract info for game: {} with contract ID: {}", 
				ethContractInfoEntity.getGameId(), ethContractInfoEntity.getEthContractId());
			return true;
		} catch (Exception e) {
			logger.error("Error while saving ETH contract info for game: {} with contract ID: {} - Error: {}", 
				ethContractInfoEntity.getGameId(), ethContractInfoEntity.getEthContractId(), e.getMessage(), e);
			return false;
		}
	}

}
