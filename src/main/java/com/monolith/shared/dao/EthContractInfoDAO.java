package com.monolith.shared.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.monolith.shared.sqldb.GamesJdbcTemplateFactory;
import com.monolith.tokenmint.beans.ETHContractInfo;
import com.monolith.tokenmint.beans.ItemInfoBean;

@Service
public class EthContractInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(GameConfigDAO.class);
	
	
	@Autowired
	GamesJdbcTemplateFactory gamesJdbcTemplateFactory;
	

	public ETHContractInfo getEthContractInfoForGame(String gameId, String ethContractId) {
		JdbcTemplate jdbcTemplate = gamesJdbcTemplateFactory.getJdbcTemplateForGame(gameId);
		if(jdbcTemplate != null) {
			String query = "SELECT * FROM eth_contract_info WHERE eth_contract_id=?";
			try {
				List<ETHContractInfo> ethContractInfoList= jdbcTemplate.query(query, new BeanPropertyRowMapper<ETHContractInfo>(ETHContractInfo.class),ethContractId);
				if(ethContractInfoList == null || ethContractInfoList.size()==0) {
					return null;
				}else {
					return ethContractInfoList.get(0);
				}
			}catch (Exception e) {
				logger.error("Error While fetching EthContract Info for game {} and Contract id {} error :: {}",gameId,ethContractId,e.getMessage());
			}
		}
		return null;
	}

}
