package com.monolith.shared.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.monolith.shared.sqldb.GamesJdbcTemplateFactory;
import com.monolith.tokenmint.beans.ItemInfoBean;
import com.monolith.tokenmint.beans.OperatorInfoBean;

@Service
public class OperatorInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(OperatorInfoDAO.class);
	
	@Autowired
	GamesJdbcTemplateFactory gamesJdbcTemplateFactory;
	
	public OperatorInfoBean getOperatorInfo(String gameId,String operatorId) {
		JdbcTemplate jdbcTemplate = gamesJdbcTemplateFactory.getJdbcTemplateForGame(gameId);
		if(jdbcTemplate != null) {
			String query = "SELECT * FROM operator_info WHERE operator_id=?";
			try {
				List<OperatorInfoBean> operatorInfoList= jdbcTemplate.query(query, new BeanPropertyRowMapper<OperatorInfoBean>(OperatorInfoBean.class),operatorId);
				if(operatorInfoList == null || operatorInfoList.size()==0) {
					return null;
				}else {
					return operatorInfoList.get(0);
				}
			}catch (Exception e) {
				logger.error("Error While fetching operatorInfoList for game {} and operator Id {} error :: {}",gameId,operatorId,e.getMessage());
			}
		}
		return null;
	}

}
