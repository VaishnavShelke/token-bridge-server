package com.monolith.tokenbank.service;

import com.monolith.tokenbank.helper.GameInfoHelper;
import com.monolith.tokenbank.helper.TokenBankUserCredsHelper;
import com.monolith.tokenbank.dto.OnBoardGameRequest;
import com.monolith.tokenbank.dto.OnboardGameResponse;
import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserResponse;
import com.monolith.tokenbank.helper.TokenBankConstants;
import com.monolith.tokenmint.beans.GameInfo;
import com.monolith.tokenmint.create.dao.GameInfoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.monolith.tokenbank.helper.TokenBankConstants.USER_VALIDATION_FAILED;
import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

@Service
public class TokenBankUserService {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankUserService.class);

    @Autowired
    private TokenBankUserCredsHelper tokenBankUserCredsHelper;

    @Autowired
    private GameInfoHelper gameInfoHelper;

    public TokenBankCreateUserResponse createUser(TokenBankCreateUserRequest createUserRequest) {
        return tokenBankUserCredsHelper.createUser(createUserRequest);
    }

    public OnboardGameResponse onboardGame(OnBoardGameRequest onBoardGameRequest) {
        String gameId = null;
        try{
            gameId = validateUserHasAccessToGameAndReturnGameId(onBoardGameRequest.getUsername(),onBoardGameRequest.getGameId());
            onBoardGameRequest.setGameId(gameId);
        }catch (Exception ex){
            logger.error("{} User doe not have access to the game: {} :: {}", TOKEN_BANK_PREPEND, ex.getMessage());
            return  new OnboardGameResponse(USER_VALIDATION_FAILED,ex.getMessage());
        }

        GameInfo gameInfo = null;
        try{
            gameInfo = gameInfoHelper.onBoardGame(onBoardGameRequest);
        }catch (Exception ex){
            logger.error("{}Game onboarding failed for gameId: {} :: {}", TOKEN_BANK_PREPEND, gameId, ex.getMessage());
            return new OnboardGameResponse(USER_VALIDATION_FAILED,ex.getMessage());
        }
        logger.info("{}Successfully onboarded game: {} for user: {}", TOKEN_BANK_PREPEND, gameId, onBoardGameRequest.getUsername());
        OnboardGameResponse onboardGameResponse = new OnboardGameResponse(TokenBankConstants.STATUS_CODE_SUCCESS, TokenBankConstants.MESSAGE_GAME_ONBOARDED);
        onboardGameResponse.setGameInfo(gameInfo);
        return onboardGameResponse;
    }

    private String validateUserHasAccessToGameAndReturnGameId(String username, String gameId) {
        try{
            gameId = tokenBankUserCredsHelper.validateUserHasAccessToGameAndReturnGameId(username,gameId);
        }catch (Exception ex){
            logger.error("{}User validation failed for user: {} :: {}", TOKEN_BANK_PREPEND, username, gameId);
            throw ex;
        }
        return gameId;
    }

    public OnboardGameResponse editGame(OnBoardGameRequest onBoardGameRequest) {
        String gameId = null;
        try{
            gameId = validateUserHasAccessToGameAndReturnGameId(onBoardGameRequest.getUsername(),onBoardGameRequest.getGameId());
            onBoardGameRequest.setGameId(gameId);
        }catch (Exception ex){
            logger.error("{} User does not have access to the game: {} :: {}", TOKEN_BANK_PREPEND, ex.getMessage());
            return new OnboardGameResponse(USER_VALIDATION_FAILED,ex.getMessage());
        }

        GameInfo gameInfo = null;
        try{
            gameInfo = gameInfoHelper.editGame(onBoardGameRequest);
        }catch (Exception ex){
            logger.error("{}Game editing failed for gameId: {} :: {}", TOKEN_BANK_PREPEND, gameId, ex.getMessage());
            return new OnboardGameResponse(USER_VALIDATION_FAILED,ex.getMessage());
        }
        logger.info("{}Successfully edited game: {} for user: {}", TOKEN_BANK_PREPEND, gameId, onBoardGameRequest.getUsername());
        OnboardGameResponse onboardGameResponse = new OnboardGameResponse(TokenBankConstants.STATUS_CODE_SUCCESS, TokenBankConstants.MESSAGE_GAME_EDITED);
        onboardGameResponse.setGameInfo(gameInfo);
        return onboardGameResponse;
    }
}
