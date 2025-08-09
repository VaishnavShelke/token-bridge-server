package com.monolith.shared.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.entities.GameItemsOnChainInfoBean;
import com.monolith.tokenmint.repository.GameItemsOnChainInfoRepository;

@Service
public class GameItemsOnChainInfoDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GameItemsOnChainInfoDAO.class);

	@Autowired
	private GameItemsOnChainInfoRepository gameItemsOnChainInfoRepository;

	public GameItemsOnChainInfoBean getGameOnChainInfoById(String gameId, String itemId) {
		try {
			return gameItemsOnChainInfoRepository.findByGameIdAndItemId(gameId, itemId).orElse(null);
		}catch (Exception e) {
			logger.error("Error While fetching game_items_on_chain_info for game {} and item id {} error :: {}",gameId,itemId,e.getMessage());
			return null;
		}
	}
	


}
