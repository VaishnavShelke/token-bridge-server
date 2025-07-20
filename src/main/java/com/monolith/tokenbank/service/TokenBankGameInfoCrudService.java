package com.monolith.tokenbank.service;

import com.monolith.tokenbank.dto.AllGamesResponse;
import com.monolith.tokenbank.dto.OnBoardGameRequest;
import com.monolith.tokenbank.dto.OnboardGameResponse;
import com.monolith.tokenbank.helper.GameInfoHelper;
import com.monolith.tokenbank.helper.TokenBankConstants;
import com.monolith.tokenbank.helper.TokenBankUserCredsHelper;
import com.monolith.tokenmint.beans.GameInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

@Service
public class TokenBankGameInfoCrudService {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankGameInfoCrudService.class);

    @Autowired
    private TokenBankUserCredsHelper tokenBankUserCredsHelper;

    @Autowired
    private GameInfoHelper gameInfoHelper;

    public OnboardGameResponse onboardGame(OnBoardGameRequest onBoardGameRequest) {
        String gameId = validateUserHasAccessToGameAndReturnGameId(onBoardGameRequest.getUsername(),onBoardGameRequest.getGameId());
        onBoardGameRequest.setGameId(gameId);
        GameInfo gameInfo = gameInfoHelper.onBoardGame(onBoardGameRequest);
        logger.info("{}Successfully onboarded game: {} for user: {}", TOKEN_BANK_PREPEND, gameId, onBoardGameRequest.getUsername());
        OnboardGameResponse onboardGameResponse = new OnboardGameResponse(TokenBankConstants.STATUS_CODE_SUCCESS, TokenBankConstants.MESSAGE_GAME_ONBOARDED);
        onboardGameResponse.setGameInfo(gameInfo);
        return onboardGameResponse;
    }

    public OnboardGameResponse editGame(OnBoardGameRequest onBoardGameRequest) {
        String gameId = validateUserHasAccessToGameAndReturnGameId(onBoardGameRequest.getUsername(),onBoardGameRequest.getGameId());
        GameInfo gameInfo = gameInfoHelper.editGame(onBoardGameRequest);
        logger.info("{}Successfully edited game: {} for user: {}", TOKEN_BANK_PREPEND, gameId, onBoardGameRequest.getUsername());
        OnboardGameResponse onboardGameResponse = new OnboardGameResponse(TokenBankConstants.STATUS_CODE_SUCCESS, TokenBankConstants.MESSAGE_GAME_EDITED);
        onboardGameResponse.setGameInfo(gameInfo);
        return onboardGameResponse;
    }

    public OnboardGameResponse deleteGame(OnBoardGameRequest onBoardGameRequest) {
        String gameId = validateUserHasAccessToGameAndReturnGameId(onBoardGameRequest.getUsername(),onBoardGameRequest.getGameId());
        onBoardGameRequest.setGameId(gameId);
        gameInfoHelper.deleteGame(onBoardGameRequest);
        logger.info("{}Successfully deleted game: {} for user: {}", TOKEN_BANK_PREPEND, gameId, onBoardGameRequest.getUsername());
        return new OnboardGameResponse(TokenBankConstants.STATUS_CODE_SUCCESS, TokenBankConstants.MESSAGE_GAME_DELETED);
    }

    public AllGamesResponse getAllGames() {
        logger.info("{} :: Received request to get all games", TOKEN_BANK_PREPEND);
        List<GameInfo> games = gameInfoHelper.getAllGames();
        logger.info("{}Successfully retrieved {} games", TOKEN_BANK_PREPEND, games.size());
        AllGamesResponse response = new AllGamesResponse(TokenBankConstants.STATUS_CODE_SUCCESS, "Fetched All Games Successfully");
        response.setGames(games);
        return response;
    }



    public String validateUserHasAccessToGameAndReturnGameId(String username, String gameId) {
        return tokenBankUserCredsHelper.validateUserHasAccessToGameAndReturnGameId(username, gameId);
    }
}
