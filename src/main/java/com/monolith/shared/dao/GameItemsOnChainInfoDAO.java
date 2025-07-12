package com.monolith.shared.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.beans.GameItemsOnChainInfoBean;

@Service
public class GameItemsOnChainInfoDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GameItemsOnChainInfoDAO.class);

	@Autowired
	@Qualifier("tokenmintjdbctemplate")
	JdbcTemplate globalJdbcTemplate;

	public GameItemsOnChainInfoBean getGameOnChainInfoById(String gameId, String itemId) {

		String query = "SELECT * FROM game_items_on_chain_info WHERE item_id=?";
		try {
			List<GameItemsOnChainInfoBean> itemOnchainInfoList= globalJdbcTemplate.query(query, new BeanPropertyRowMapper<GameItemsOnChainInfoBean>(GameItemsOnChainInfoBean.class),itemId);
			if(itemOnchainInfoList == null || itemOnchainInfoList.size()==0) {
				return null;
			}else {
				return itemOnchainInfoList.get(0);
			}
		}catch (Exception e) {
			logger.error("Error While fetching game_items_on_chain_info for game {} and item id {} error :: {}",gameId,itemId,e.getMessage());
		}
		return null;
	}
	


}
