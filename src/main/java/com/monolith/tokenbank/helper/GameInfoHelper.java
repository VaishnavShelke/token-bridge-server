package com.monolith.tokenbank.helper;

import com.monolith.shared.utils.Utility;
import com.monolith.tokenbank.dto.OnBoardGameRequest;
import com.monolith.tokenmint.entities.GameInfoEntity;
import com.monolith.tokenmint.dao.GameInfoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

@Service
public class GameInfoHelper {

    private static final Logger logger = LoggerFactory.getLogger(GameInfoHelper.class);

    @Autowired
    GameInfoDAO gameInfoDAO;


    public GameInfoEntity onBoardGame(OnBoardGameRequest onBoardGameRequest) {
        ValidationUtils.validateOnBoardGameRequest(onBoardGameRequest);
        String gameId = onBoardGameRequest.getGameId();
        GameInfoEntity requestGameInfoEntity = onBoardGameRequest.getGameInfoEntity();
        if (gameInfoDAO.doesGameExist(gameId)) {
            logger.info("{}Game already exists with ID: {}, retrieving existing game info", TOKEN_BANK_PREPEND, gameId);
            GameInfoEntity existingGameInfoEntity = gameInfoDAO.getGameInfoByGameId(gameId);
            if (existingGameInfoEntity != null) {
                return existingGameInfoEntity;
            }
        }
        GameInfoEntity gameInfoEntity = new GameInfoEntity();
        gameInfoEntity.setGameId(gameId);
        TokenBankPojoHelper.getGameInfoFromOnBoardGameRequest(gameInfoEntity, requestGameInfoEntity);

        gameInfoDAO.saveGameInfo(gameInfoEntity);

        return gameInfoEntity;
    }

    public GameInfoEntity editGame(OnBoardGameRequest onBoardGameRequest) {
        ValidationUtils.validateOnBoardGameRequest(onBoardGameRequest);
        String gameId = onBoardGameRequest.getGameId();
        GameInfoEntity requestGameInfoEntity = onBoardGameRequest.getGameInfoEntity();
        if (!gameInfoDAO.doesGameExist(gameId)) {
            logger.error("{}Game does not exist with ID: {}, cannot edit non-existent game", TOKEN_BANK_PREPEND, gameId);
            throw new RuntimeException("Game does not exist with ID: " + gameId);
        }
        GameInfoEntity currentGameInfoEntity = gameInfoDAO.getGameInfoByGameId(gameId);
        if (currentGameInfoEntity == null) {
            logger.error("{}Failed to retrieve current game info for ID: {}", TOKEN_BANK_PREPEND, gameId);
            throw new RuntimeException("Failed to retrieve current game information");
        }
        TokenBankPojoHelper.setGameInfoFromOnBoardGameRequest(currentGameInfoEntity, requestGameInfoEntity);
        gameInfoDAO.updateGameInfo(currentGameInfoEntity);
        return currentGameInfoEntity;
    }

    public void deleteGame(OnBoardGameRequest onBoardGameRequest) {
        if (onBoardGameRequest == null ||
                Utility.isNullOrEmpty(onBoardGameRequest.getGameId())) {

            throw new IllegalArgumentException("Game ID and game name are required");
        }
        String gameId = onBoardGameRequest.getGameId();
        if (!gameInfoDAO.doesGameExist(gameId)) {
            logger.error("{}Game does not exist with ID: {}, cannot delete non-existent game", TOKEN_BANK_PREPEND, gameId);
            throw new RuntimeException("Game does not exist with ID: " + gameId);
        }
        gameInfoDAO.deleteGameInfo(gameId);
    }

    public List<GameInfoEntity> getAllGames() {
        return gameInfoDAO.getAllGames();
    }
}
