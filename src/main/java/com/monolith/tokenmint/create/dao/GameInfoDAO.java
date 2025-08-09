package com.monolith.tokenmint.create.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monolith.tokenmint.entities.GameInfo;
import com.monolith.tokenmint.repository.GameInfoRepository;

@Service
public class GameInfoDAO {

    private static final Logger logger = LoggerFactory.getLogger(GameInfoDAO.class);

    @Autowired
    private GameInfoRepository gameInfoRepository;


    public GameInfo getGameInfoByGameId(String gameId) {
        return gameInfoRepository.findById(gameId).orElse(null);
    }

    public void saveGameInfo(GameInfo gameInfo) {
        try {
            gameInfoRepository.save(gameInfo);
            logger.info("Successfully saved game info for gameId: {}, gameName: {}", gameInfo.getGameId(), gameInfo.getGameName());
        } catch (Exception e) {
            logger.error("Failed to save game info: {}", e.getMessage());
            throw new RuntimeException("Failed to Save The Game");
        }
    }

    public boolean doesGameExist(String gameId) {
        try {
            return gameInfoRepository.existsById(gameId);
        } catch (Exception e) {
            logger.error("Error while checking if game exists for gameId: {} :: {}", gameId, e.getMessage());
            return false;
        }
    }

    public void updateGameInfo(GameInfo gameInfo) {
        try {
            if (gameInfoRepository.existsById(gameInfo.getGameId())) {
                gameInfoRepository.save(gameInfo);
                logger.info("Successfully updated game info for gameId: {}, gameName: {}", gameInfo.getGameId(), gameInfo.getGameName());
            } else {
                logger.warn("Failed to update game info for gameId: {} - game not found", gameInfo.getGameId());
                throw new RuntimeException("Failed to update game info - game not found");
            }
        } catch (Exception e) {
            logger.error("Error while updating game info: {}", e.getMessage());
            throw new RuntimeException("Failed to update game info");
        }
    }

    public java.util.List<GameInfo> getAllGames() {
        try {
            java.util.List<GameInfo> gameInfoList = gameInfoRepository.findAll()
                .stream()
                .filter(game -> !"DEFAULT_GAME".equals(game.getGameId()))
                .sorted((g1, g2) -> g1.getGameId().compareTo(g2.getGameId()))
                .collect(java.util.stream.Collectors.toList());
            logger.info("Retrieved {} games from database", gameInfoList.size());
            return gameInfoList;
        } catch (Exception e) {
            logger.error("Error while fetching all games :: {}", e.getMessage());
            return null;
        }
    }

    public void deleteGameInfo(String gameId) {
        try {
            if (gameInfoRepository.existsById(gameId)) {
                gameInfoRepository.deleteById(gameId);
                logger.info("Successfully deleted game info for gameId: {}", gameId);
            } else {
                logger.warn("Failed to delete game info for gameId: {} - game not found", gameId);
                throw new RuntimeException("Failed to delete game - game not found");
            }
        } catch (Exception e) {
            logger.error("Error while deleting game info: {}", e.getMessage());
            throw new RuntimeException("Failed to delete game");
        }
    }
}
