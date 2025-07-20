package com.monolith.tokenmint.create.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        List<GameInfo> gameInfoList = globalJdbcTemplate.query(query, new BeanPropertyRowMapper<GameInfo>(GameInfo.class), gameId);
        if (gameInfoList == null || gameInfoList.size() == 0) {
            return null;
        } else {
            return gameInfoList.get(0);
        }
    }

    public void saveGameInfo(GameInfo gameInfo) {
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

        } else {
            throw new RuntimeException("Failed to Save The Game");
        }
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

    public void updateGameInfo(GameInfo gameInfo) {

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
        } else {
            logger.warn("Failed to update game info for gameId: {}, gameName: {}", gameInfo.getGameId(), gameInfo.getGameName());
            throw new RuntimeException("Failed to update game info");
        }

    }

    public List<GameInfo> getAllGames() {
        String query = "SELECT * FROM game_info WHERE game_id != 'DEFAULT_GAME' ORDER BY game_id";
        try {
            List<GameInfo> gameInfoList = globalJdbcTemplate.query(query, new BeanPropertyRowMapper<GameInfo>(GameInfo.class));
            logger.info("Retrieved {} games from database", gameInfoList.size());
            return gameInfoList;
        } catch (Exception e) {
            logger.error("Error while fetching all games :: {}", e.getMessage());
            return null;
        }
    }

    public void deleteGameInfo(String gameId) {

        String query = "DELETE FROM game_info WHERE game_id = ?";
        int rowsDeleted = globalJdbcTemplate.update(query, gameId);

        if (rowsDeleted > 0) {
            logger.info("Successfully deleted game info for gameId: {}", gameId);
        } else {
            logger.warn("Failed to delete game info for gameId: {} - no rows affected", gameId);
            throw new RuntimeException("Failed to delete game");
        }
    }
}
