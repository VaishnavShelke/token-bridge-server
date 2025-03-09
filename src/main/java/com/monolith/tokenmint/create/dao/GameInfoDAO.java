package com.monolith.tokenmint.create.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.beans.GameInfo;

@Service
public class GameInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(GameInfoDAO.class);
	
	@Autowired
	@Qualifier("tokenmintjdbctemplate")
	JdbcTemplate globalJdbcTemplate;
	
	
	public GameInfo getGameInfoByGameId(String gameId) {
		String query = "SELECT * FROM game_info WHERE game_id=?";
		try {
			List<GameInfo> gameInfoList= globalJdbcTemplate.query(query, new BeanPropertyRowMapper<GameInfo>(GameInfo.class),gameId);
			if(gameInfoList == null || gameInfoList.size()==0) {
				return null;
			}else {
				return gameInfoList.get(0);
			}
		}catch (Exception e) {
			logger.error("Error Whil fetching gameInfo Info for game {} {}",gameId,e.getMessage());
		}
		return null;
	}

}
