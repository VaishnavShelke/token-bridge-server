package com.monolith.shared.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.beans.ETHContractInfo;
import com.monolith.tokenmint.repository.ETHContractInfoRepository;


@Service
public class EthContractInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(EthContractInfoDAO.class);

	@Autowired
	private ETHContractInfoRepository ethContractInfoRepository;
	

	public ETHContractInfo getEthContractInfoForGame(String gameId, String ethContractId) {
		try {
			return ethContractInfoRepository.findByGameIdAndEthContractId(gameId, ethContractId).orElse(null);
		}catch (Exception e) {
			logger.error("Error While fetching EthContract Info for game {} and Contract id {} error :: {}",gameId,ethContractId,e.getMessage());
			return null;
		}
	}

	public boolean insertEthContractInfo(ETHContractInfo ethContractInfo) {
		try {
			ethContractInfoRepository.save(ethContractInfo);
			logger.info("Successfully saved ETH contract info for game: {} with contract ID: {}", 
				ethContractInfo.getGameId(), ethContractInfo.getEthContractId());
			return true;
		} catch (Exception e) {
			logger.error("Error while saving ETH contract info for game: {} with contract ID: {} - Error: {}", 
				ethContractInfo.getGameId(), ethContractInfo.getEthContractId(), e.getMessage(), e);
			return false;
		}
	}

}
