package com.monolith.shared.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.beans.ETHContractInfo;


@Service
public class EthContractInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(GameConfigDAO.class);


	@Autowired
	@Qualifier("tokenmintjdbctemplate")
	JdbcTemplate globalJdbcTemplate;
	

	public ETHContractInfo getEthContractInfoForGame(String gameId, String ethContractId) {
		String query = "SELECT * FROM eth_contract_info WHERE eth_contract_id=?";
		try {
			List<ETHContractInfo> ethContractInfoList= globalJdbcTemplate.query(query, new BeanPropertyRowMapper<ETHContractInfo>(ETHContractInfo.class),ethContractId);
			if(ethContractInfoList == null || ethContractInfoList.size()==0) {
				return null;
			}else {
				return ethContractInfoList.get(0);
			}
		}catch (Exception e) {
			logger.error("Error While fetching EthContract Info for game {} and Contract id {} error :: {}",gameId,ethContractId,e.getMessage());
		}
		return null;
	}

}
