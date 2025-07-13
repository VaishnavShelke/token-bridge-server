package com.monolith.tokenbank.helper;

import com.monolith.shared.utils.Utility;
import com.monolith.tokenbank.beans.TokenBankUserCreds;
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
        try {
            // Validate input
            if (createUserRequest == null ||
                    Utility.isNullOrEmpty(createUserRequest.getUsername()) ||
                    Utility.isNullOrEmpty(createUserRequest.getPassword())) {

                logger.error("{}Validation failed - Username and password are required",TOKEN_BANK_PREPEND);
                return new TokenBankCreateUserResponse(TokenBankConstants.STATUS_CODE_VALIDATION_FAILED, TokenBankConstants.MESSAGE_VALIDATION_FAILED);
            }

            String username = createUserRequest.getUsername();
            String password = createUserRequest.getPassword();

            // Check if user already exists
            if (tokenBankUserCredsDAO.doesUserExist(username)) {
                logger.warn("{}User already exists with username: {}",TOKEN_BANK_PREPEND, username);
                TokenBankCreateUserResponse response = new TokenBankCreateUserResponse(TokenBankConstants.STATUS_CODE_USER_ALREADY_EXISTS, TokenBankConstants.MESSAGE_USER_ALREADY_EXISTS);
                response.setUsername(username);
                return response;
            }

            // Create user credentials bean
            TokenBankUserCreds userCreds = new TokenBankUserCreds();
            userCreds.setUsername(username);
            userCreds.setPassword(password);
            userCreds.setGameId(TokenBankConstants.DEFAULT_GAME_ID);
            userCreds.setUserRole(TokenBankConstants.DEFAULT_USER_ROLE);

            // Save user to database
            boolean saved = tokenBankUserCredsDAO.saveTokenBankUserCreds(userCreds);

            if (saved) {
                logger.info("{}Successfully created user: {}",TOKEN_BANK_PREPEND, username);
                TokenBankCreateUserResponse response = new TokenBankCreateUserResponse(TokenBankConstants.STATUS_CODE_SUCCESS, TokenBankConstants.MESSAGE_SUCCESS);
                response.setUsername(username);
                return response;
            } else {
                logger.error("{}Failed to save user to database: {}",TOKEN_BANK_PREPEND, username);
                return new TokenBankCreateUserResponse(TokenBankConstants.STATUS_CODE_INTERNAL_ERROR, TokenBankConstants.MESSAGE_INTERNAL_ERROR);
            }

        } catch (Exception e) {
            logger.error("{}Error creating user: {}",TOKEN_BANK_PREPEND, e.getMessage(), e);
            return new TokenBankCreateUserResponse(TokenBankConstants.STATUS_CODE_INTERNAL_ERROR, TokenBankConstants.MESSAGE_INTERNAL_ERROR);
        }
    }

    public String validateUserHasAccessToGameAndReturnGameId(String username,String requestedGameId) {

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
                        TOKEN_BANK_PREPEND,username, requestedGameId);
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
}
