package com.monolith.shared.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.beans.OperatorInfoBean;
import com.monolith.tokenmint.repository.OperatorInfoRepository;

@Service
public class OperatorInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(OperatorInfoDAO.class);

	@Autowired
	private OperatorInfoRepository operatorInfoRepository;
	
	public OperatorInfoBean getOperatorInfo(String gameId,String operatorId) {
		try {
			return operatorInfoRepository.findById(operatorId).orElse(null);
		}catch (Exception e) {
			logger.error("Error While fetching operatorInfoList for game {} and operator Id {} error :: {}",gameId,operatorId,e.getMessage());
			return null;
		}
	}

}
