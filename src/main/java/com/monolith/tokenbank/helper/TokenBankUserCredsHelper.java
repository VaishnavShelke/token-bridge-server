package com.monolith.tokenbank.helper;

import com.monolith.shared.utils.Utility;
import com.monolith.tokenbank.entities.TokenBankUserCreds;
import com.monolith.tokenbank.dao.TokenBankUserCredsDAO;
import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

@Service
public class TokenBankUserCredsHelper {
    private static final Logger logger = LoggerFactory.getLogger(TokenBankUserCredsHelper.class);

    @Autowired
    private TokenBankUserCredsDAO tokenBankUserCredsDAO;

    public TokenBankCreateUserResponse createUser(TokenBankCreateUserRequest createUserRequest) {
        String username = createUserRequest.getUsername();
        if (tokenBankUserCredsDAO.doesUserExist(username)) {
            logger.warn("{}User already exists with username: {}", TOKEN_BANK_PREPEND, username);
            TokenBankCreateUserResponse response = new TokenBankCreateUserResponse(TokenBankConstants.STATUS_CODE_USER_ALREADY_EXISTS, TokenBankConstants.MESSAGE_USER_ALREADY_EXISTS);
            response.setUsername(username);
            return response;
        }
        TokenBankUserCreds userCreds = new TokenBankUserCreds();
        TokenBankPojoHelper.setTokenBankUserCredsFromTokenBankCreateUserRequest(userCreds,createUserRequest);
        tokenBankUserCredsDAO.saveTokenBankUserCreds(userCreds);
        TokenBankCreateUserResponse response = new TokenBankCreateUserResponse(TokenBankConstants.STATUS_CODE_SUCCESS, TokenBankConstants.MESSAGE_SUCCESS);
        response.setUsername(username);
        return response;
    }

    public String validateUserHasAccessToGameAndReturnGameId(String username, String requestedGameId) {

        TokenBankUserCreds userCreds = tokenBankUserCredsDAO.getUserByUsername(username);
        if (userCreds == null) {
            logger.error("{}User not found: {}", TOKEN_BANK_PREPEND, username);
            throw new IllegalArgumentException(TokenBankConstants.MESSAGE_USER_NOT_FOUND);
        }

        String currentGameId = userCreds.getGameId();

        if (Utility.isNullOrEmpty(currentGameId) || TokenBankConstants.DEFAULT_GAME_ID.equals(currentGameId)) {
            logger.info("{}User {} has no game associated :: Need to generate one game Id",
                    TOKEN_BANK_PREPEND, username);
            requestedGameId = TokenBankUtil.generateRandomGameId();
            boolean updated = tokenBankUserCredsDAO.updateUserGameIdAndRole(
                    username, requestedGameId, TokenBankConstants.ADMIN_USER_ROLE);
            if (!updated) {
                logger.error("{}Failed to update user {} with game {} and ADMIN role",
                        TOKEN_BANK_PREPEND, username, requestedGameId);
                throw new RuntimeException("Failed to update user game association");
            }
        } else {
            if (!currentGameId.equals(requestedGameId)) {
                logger.error("{}User {} is associated with game {} but requested game {}",
                        TOKEN_BANK_PREPEND, username, currentGameId, requestedGameId);
                throw new IllegalArgumentException(TokenBankConstants.MESSAGE_GAME_MISMATCH +
                        ". User is associated with game: " + currentGameId +
                        " but requested game: " + requestedGameId);
            }
            logger.info("{}User {} already has access to game: {}",
                    TOKEN_BANK_PREPEND, username, currentGameId);
        }
        return requestedGameId;
    }

    public void validateUserIsAdminForGame(String username, String gameId) {
        try {
            TokenBankUserCreds userCreds = tokenBankUserCredsDAO.getUserByUsername(username);
            if (userCreds == null) {
                logger.error("{}User not found: {}", TOKEN_BANK_PREPEND, username);
                throw new IllegalArgumentException(TokenBankConstants.MESSAGE_USER_NOT_FOUND);
            }

            if (!gameId.equals(userCreds.getGameId())) {
                logger.error("{}User {} is not associated with game {}", TOKEN_BANK_PREPEND, username, gameId);
                throw new IllegalArgumentException(TokenBankConstants.MESSAGE_GAME_MISMATCH +
                        ". User is associated with game: " + userCreds.getGameId() +
                        " but requested game: " + gameId);
            }

            if (!TokenBankConstants.ADMIN_USER_ROLE.equals(userCreds.getUserRole())) {
                logger.error("{}User {} does not have admin role for game {}", TOKEN_BANK_PREPEND, username, gameId);
                throw new IllegalArgumentException("User does not have admin privileges for the requested game");
            }

            logger.info("{}User {} is validated as admin for game {}", TOKEN_BANK_PREPEND, username, gameId);
        } catch (Exception e) {
            logger.error("{}Error validating admin access for user {} and game {}: {}", TOKEN_BANK_PREPEND, username, gameId, e.getMessage());
            throw new RuntimeException("Failed to validate admin access: " + e.getMessage(), e);
        }
    }
}
