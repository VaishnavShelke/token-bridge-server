package com.monolith.shared.dao;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.monolith.shared.sqldb.SQLDataSourceInfo;
import com.monolith.shared.utils.GameServerConstants.GameServerEndpoint;
import com.monolith.tokenmint.beans.GameConfigInfo;

@Service
public class GameConfigDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GameConfigDAO.class);

	@Autowired
	@Qualifier("tokenmintjdbctemplate")
	JdbcTemplate globalJdbcTemplate;

	
	public String getGameServerEndpointURL(String gameId, GameServerEndpoint gsEdEnum) {
		GameConfigInfo gameConfigInfo = getGameConfig(gameId, GameConfigInfo.GAMESERVER_ENDPOINTS);
		HashMap<String,String> endpointUrls = gameConfigInfo.getParsedEndpointUrlMap();
		if(endpointUrls != null) {
			return endpointUrls.get(gsEdEnum.getValue());
		}
		return null;
	}
	
	public GameConfigInfo getGameConfig(String gameId,String configType) {
		String query = "SELECT * FROM game_config WHERE game_id=? AND config_type=?";
		try {
			List<GameConfigInfo> gameConfigInfoList= globalJdbcTemplate.query(query, new BeanPropertyRowMapper<GameConfigInfo>(GameConfigInfo.class),gameId,configType);
			if(gameConfigInfoList == null || gameConfigInfoList.size()==0) {
				return null;
			}else {
				return gameConfigInfoList.get(0);
			}
		}catch (Exception e) {
			logger.error("Error Whil fetching gameDataSource Info for game {} {}",gameId,e.getMessage());
		}
		return null;
	}
	
}

//private String gameId;
//private Map<String,ETHContractInfo> ethContractInfoMap;
//private Map<String,ItemInfoBean> gameItemsMap; 
	
