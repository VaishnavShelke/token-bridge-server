package com.monolith.tokenbank.helper;

import com.monolith.shared.utils.Utility;
import com.monolith.tokenbank.dto.AddGameItemRequest;
import com.monolith.tokenbank.dto.OnBoardGameRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserResponse;
import com.monolith.tokenbank.dto.UnAliveItemRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;

import static com.monolith.tokenbank.helper.TokenBankConstants.STATUS_CODE_VALIDATION_FAILED;
import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

/**
 * Utility class for validation operations
 * Contains static methods for common validation scenarios
 */
public class ValidationUtils {


    public static void validateGetAllItemsForGameInput(String gameId) {
        if (Utility.isNullOrEmpty(gameId)) {
            throw new RuntimeException("Invalid Input No Game Id Present");
        }
    }

    public static void validateAddGameItemRequest(AddGameItemRequest addGameItemRequest) {
        if (StringUtils.isEmpty(addGameItemRequest.getUsername())) {
            throw new RuntimeException("Username is required");
        }

        if (StringUtils.isEmpty(addGameItemRequest.getGameId())) {
            throw new RuntimeException("GameId is required");
        }

        // itemId validation removed - will be auto-generated

        if (StringUtils.isEmpty(addGameItemRequest.getItemTitle())) {
            throw new RuntimeException("ItemTitle is required");
        }
    }

    public static void validateOnBoardGameRequest(OnBoardGameRequest onBoardGameRequest) {
        if (onBoardGameRequest == null ||
                Utility.isNullOrEmpty(onBoardGameRequest.getGameId()) ||
                onBoardGameRequest.getGameInfo() == null ||
                Utility.isNullOrEmpty(onBoardGameRequest.getGameInfo().getGameName())) {

            throw new IllegalArgumentException("Game ID and game name are required");
        }
    }

    public static void validateUnAliveItemRequest(UnAliveItemRequest unAliveItemRequest) {
        if (StringUtils.isEmpty(unAliveItemRequest.getUsername())) {
            throw new RuntimeException("Username is required");
        }

        if (StringUtils.isEmpty(unAliveItemRequest.getGameId())) {
            throw new RuntimeException("GameId is required");
        }

        if (StringUtils.isEmpty(unAliveItemRequest.getItemId())) {
            throw new RuntimeException("ItemId is required");
        }
    }

    public static void validateCreateUserRequest(TokenBankCreateUserRequest createUserRequest) {
        if (StringUtils.isEmpty(createUserRequest.getUsername())) {
            throw new IllegalArgumentException("Username is required") ;
        }

        if (StringUtils.isEmpty(createUserRequest.getPassword())) {
            throw new IllegalArgumentException("Password is required") ;
        }
    }
}