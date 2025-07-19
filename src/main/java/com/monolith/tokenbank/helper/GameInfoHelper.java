package com.monolith.tokenbank.helper;

import com.monolith.shared.utils.Utility;
import com.monolith.tokenbank.dto.OnBoardGameRequest;
import com.monolith.tokenmint.beans.GameInfo;
import com.monolith.tokenmint.create.dao.GameInfoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

@Service
public class GameInfoHelper {

    private static final Logger logger = LoggerFactory.getLogger(GameInfoHelper.class);

    @Autowired
    GameInfoDAO gameInfoDAO;

    public GameInfo onBoardGame(OnBoardGameRequest onBoardGameRequest) {
        try {
            // Validate input
            if (onBoardGameRequest == null || 
                Utility.isNullOrEmpty(onBoardGameRequest.getGameId()) ||
                onBoardGameRequest.getGameInfo() == null ||
                Utility.isNullOrEmpty(onBoardGameRequest.getGameInfo().getGameName())) {
                
                logger.error("{}Validation failed - Game ID and game name are required", TOKEN_BANK_PREPEND);
                throw new IllegalArgumentException("Game ID and game name are required");
            }

            String gameId = onBoardGameRequest.getGameId();
            GameInfo requestGameInfo = onBoardGameRequest.getGameInfo();

            // Check if game already exists
            if (gameInfoDAO.doesGameExist(gameId)) {
                logger.info("{}Game already exists with ID: {}, retrieving existing game info", TOKEN_BANK_PREPEND, gameId);
                
                // Return existing game info
                GameInfo existingGameInfo = gameInfoDAO.getGameInfoByGameId(gameId);
                if (existingGameInfo != null) {
                    return existingGameInfo;
                } else {
                    logger.error("{}Game exists but failed to retrieve game info for ID: {}", TOKEN_BANK_PREPEND, gameId);
                    throw new RuntimeException("Failed to retrieve existing game information");
                }
            }

            // Create new game info
            GameInfo gameInfo = new GameInfo();
            gameInfo.setGameId(gameId);
            gameInfo.setGameName(requestGameInfo.getGameName());
            gameInfo.setGameParentCompany(requestGameInfo.getGameParentCompany());
            gameInfo.setGameLogo(requestGameInfo.getGameLogo());

            gameInfo.setApiKey(TokenBankUtil.generateApiKey());

            // Save to database
            boolean saved = gameInfoDAO.saveGameInfo(gameInfo);
            
            if (saved) {
                logger.info("{}Successfully onboarded new game: {} with name: {}", TOKEN_BANK_PREPEND, gameId, gameInfo.getGameName());
                return gameInfo;
            } else {
                logger.error("{}Failed to save game info for ID: {}", TOKEN_BANK_PREPEND, gameId);
                throw new RuntimeException("Failed to save game information to database");
            }

        } catch (IllegalArgumentException e) {
            logger.error("{}Validation error in onBoardGame: {}", TOKEN_BANK_PREPEND, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{}Error onboarding game: {}", TOKEN_BANK_PREPEND, e.getMessage(), e);
            throw new RuntimeException("Error onboarding game: " + e.getMessage(), e);
        }
    }

    public GameInfo editGame(OnBoardGameRequest onBoardGameRequest) {
        try {
            // Validate input
            if (onBoardGameRequest == null || 
                Utility.isNullOrEmpty(onBoardGameRequest.getGameId()) ||
                onBoardGameRequest.getGameInfo() == null ||
                Utility.isNullOrEmpty(onBoardGameRequest.getGameInfo().getGameName())) {
                
                logger.error("{}Validation failed - Game ID and game name are required", TOKEN_BANK_PREPEND);
                throw new IllegalArgumentException("Game ID and game name are required");
            }

            String gameId = onBoardGameRequest.getGameId();
            GameInfo requestGameInfo = onBoardGameRequest.getGameInfo();

            // Check if game exists (required for editing)
            if (!gameInfoDAO.doesGameExist(gameId)) {
                logger.error("{}Game does not exist with ID: {}, cannot edit non-existent game", TOKEN_BANK_PREPEND, gameId);
                throw new RuntimeException("Game does not exist with ID: " + gameId);
            }

            // Get current game info
            GameInfo currentGameInfo = gameInfoDAO.getGameInfoByGameId(gameId);
            if (currentGameInfo == null) {
                logger.error("{}Failed to retrieve current game info for ID: {}", TOKEN_BANK_PREPEND, gameId);
                throw new RuntimeException("Failed to retrieve current game information");
            }

            // Update game info with new values
            currentGameInfo.setGameName(requestGameInfo.getGameName());
            currentGameInfo.setGameParentCompany(requestGameInfo.getGameParentCompany());
            currentGameInfo.setGameLogo(requestGameInfo.getGameLogo());
            // Note: We don't update the API key during editing

            // Save updated game info
            boolean updated = gameInfoDAO.updateGameInfo(currentGameInfo);
            
            if (updated) {
                logger.info("{}Successfully updated game: {} with name: {}", TOKEN_BANK_PREPEND, gameId, currentGameInfo.getGameName());
                return currentGameInfo;
            } else {
                logger.error("{}Failed to update game info for ID: {}", TOKEN_BANK_PREPEND, gameId);
                throw new RuntimeException("Failed to update game information in database");
            }

        } catch (IllegalArgumentException e) {
            logger.error("{}Validation error in editGame: {}", TOKEN_BANK_PREPEND, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{}Error editing game: {}", TOKEN_BANK_PREPEND, e.getMessage(), e);
            throw new RuntimeException("Error editing game: " + e.getMessage(), e);
        }
    }

    public boolean deleteGame(OnBoardGameRequest onBoardGameRequest) {
        try {
            // Validate input
            if (onBoardGameRequest == null || 
                Utility.isNullOrEmpty(onBoardGameRequest.getGameId())) {
                
                logger.error("{}Validation failed - Game ID is required for deletion", TOKEN_BANK_PREPEND);
                throw new IllegalArgumentException("Game ID is required for deletion");
            }

            String gameId = onBoardGameRequest.getGameId();

            // Check if game exists (required for deletion)
            if (!gameInfoDAO.doesGameExist(gameId)) {
                logger.error("{}Game does not exist with ID: {}, cannot delete non-existent game", TOKEN_BANK_PREPEND, gameId);
                throw new RuntimeException("Game does not exist with ID: " + gameId);
            }

            // Delete the game
            boolean deleted = gameInfoDAO.deleteGameInfo(gameId);
            
            if (deleted) {
                logger.info("{}Successfully deleted game: {}", TOKEN_BANK_PREPEND, gameId);
                return true;
            } else {
                logger.error("{}Failed to delete game info for ID: {}", TOKEN_BANK_PREPEND, gameId);
                throw new RuntimeException("Failed to delete game information from database");
            }

        } catch (IllegalArgumentException e) {
            logger.error("{}Validation error in deleteGame: {}", TOKEN_BANK_PREPEND, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{}Error deleting game: {}", TOKEN_BANK_PREPEND, e.getMessage(), e);
            throw new RuntimeException("Error deleting game: " + e.getMessage(), e);
        }
    }
}
