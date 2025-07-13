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
			logger.error("Error While fetching gameInfo Info for game {} {}",gameId,e.getMessage());
		}
		return null;
	}

	public boolean saveGameInfo(GameInfo gameInfo) {
		try {
			String query = "INSERT INTO game_info (game_id, game_name, game_parent_company, game_logo, api_key) VALUES (?, ?, ?, ?, ?)";
			int rowsInserted = globalJdbcTemplate.update(
				query,
				gameInfo.getGameId(),
				gameInfo.getGameName(),
				gameInfo.getGameParentCompany(),
				gameInfo.getGameLogo(),
					gameInfo.getApiKey()
			);

			if (rowsInserted > 0) {
				logger.info("Successfully saved game info for gameId: {}, gameName: {}", gameInfo.getGameId(), gameInfo.getGameName());
				return true;
			} else {
				logger.warn("Failed to save game info for gameId: {}, gameName: {}", gameInfo.getGameId(), gameInfo.getGameName());
			}
		} catch (Exception e) {
			logger.error("Error while saving game info for gameId: {}, gameName: {} :: {}", gameInfo.getGameId(), gameInfo.getGameName(), e.getMessage());
		}
		return false;
	}

	public boolean doesGameExist(String gameId) {
		try {
			String query = "SELECT COUNT(*) FROM game_info WHERE game_id = ?";
			Integer count = globalJdbcTemplate.queryForObject(query, Integer.class, gameId);
			return count != null && count > 0;
		} catch (Exception e) {
			logger.error("Error while checking if game exists for gameId: {} :: {}", gameId, e.getMessage());
			return false;
		}
	}

	public boolean updateGameInfo(GameInfo gameInfo) {
		try {
			String query = "UPDATE game_info SET game_name = ?, game_parent_company = ?, game_logo = ? WHERE game_id = ?";
			int rowsUpdated = globalJdbcTemplate.update(
				query,
				gameInfo.getGameName(),
				gameInfo.getGameParentCompany(),
				gameInfo.getGameLogo(),
				gameInfo.getGameId()
			);

			if (rowsUpdated > 0) {
				logger.info("Successfully updated game info for gameId: {}, gameName: {}", gameInfo.getGameId(), gameInfo.getGameName());
				return true;
			} else {
				logger.warn("Failed to update game info for gameId: {}, gameName: {}", gameInfo.getGameId(), gameInfo.getGameName());
			}
		} catch (Exception e) {
			logger.error("Error while updating game info for gameId: {}, gameName: {} :: {}", gameInfo.getGameId(), gameInfo.getGameName(), e.getMessage());
		}
		return false;
	}
}
