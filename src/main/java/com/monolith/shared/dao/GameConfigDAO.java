package com.monolith.shared.dao;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.shared.utils.GameServerConstants.GameServerEndpoint;
import com.monolith.tokenmint.entities.GameConfigInfo;
import com.monolith.tokenmint.repository.GameConfigRepository;

@Service
public class GameConfigDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GameConfigDAO.class);

	@Autowired
	private GameConfigRepository gameConfigRepository;

	
	public String getGameServerEndpointURL(String gameId, GameServerEndpoint gsEdEnum) {
		GameConfigInfo gameConfigInfo = getGameConfig(gameId, GameConfigInfo.GAMESERVER_ENDPOINTS);
		HashMap<String,String> endpointUrls = gameConfigInfo.getParsedEndpointUrlMap();
		if(endpointUrls != null) {
			return endpointUrls.get(gsEdEnum.getValue());
		}
		return null;
	}
	
	public GameConfigInfo getGameConfig(String gameId,String configType) {
		try {
			return gameConfigRepository.findByGameIdAndConfigType(gameId, configType).orElse(null);
		}catch (Exception e) {
			logger.error("Error While fetching gameDataSource Info for game {} {}",gameId,e.getMessage());
			return null;
		}
	}
	
}

//private String gameId;
//private Map<String,ETHContractInfo> ethContractInfoMap;
//private Map<String,ItemInfoBean> gameItemsMap; 
	
