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
			logger.error("Error while fetching gameInfo Info for game {} {}",gameId,e.getMessage());
		}
		return null;
	}
	
	public List<GameInfo> getAllGames() {
		String query = "SELECT * FROM game_info ORDER BY game_id";
		try {
			List<GameInfo> gameInfoList = globalJdbcTemplate.query(query, new BeanPropertyRowMapper<GameInfo>(GameInfo.class));
			return gameInfoList;
		}catch (Exception e) {
			logger.error("Error while fetching all games: {}",e.getMessage());
		}
		return null;
	}
	
	public boolean createGame(GameInfo gameInfo) {
		String query = "INSERT INTO game_info (game_id, game_name, game_parent_company, game_logo) VALUES (?, ?, ?, ?)";
		try {
			int result = globalJdbcTemplate.update(query, 
				gameInfo.getGameId(), 
				gameInfo.getGameName(), 
				gameInfo.getGameParentCompany(), 
				gameInfo.getGameLogo()
			);
			if(result == 1) {
				logger.info("Game created successfully: {}", gameInfo.getGameId());
				return true;
			}
		}catch (Exception e) {
			logger.error("Error while creating game {}: {}",gameInfo.getGameId(), e.getMessage());
		}
		return false;
	}
	


	public boolean gameExists(String gameId) {
		String query = "SELECT COUNT(*) FROM game_info WHERE game_id=?";
		try {
			Integer count = globalJdbcTemplate.queryForObject(query, Integer.class, gameId);
			return count != null && count > 0;
		}catch (Exception e) {
			logger.error("Error while checking if game exists {}: {}",gameId, e.getMessage());
		}
		return false;
	}

}
