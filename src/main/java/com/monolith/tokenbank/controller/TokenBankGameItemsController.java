package com.monolith.tokenbank.controller;

import com.monolith.shared.utils.Utility;
import com.monolith.tokenbank.dto.AddGameItemRequest;
import com.monolith.tokenbank.dto.AddGameItemResponse;
import com.monolith.tokenbank.dto.AllGameItemsResponse;
import com.monolith.tokenbank.dto.UnAliveItemRequest;
import com.monolith.tokenbank.dto.UnAliveItemResponse;
import com.monolith.tokenbank.service.TokenBankInGameAssetsService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.monolith.tokenbank.helper.TokenBankConstants.STATUS_CODE_INTERNAL_ERROR;
import static com.monolith.tokenbank.helper.TokenBankConstants.STATUS_CODE_VALIDATION_FAILED;
import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

@RestController
@RequestMapping("/tokenbank/items")
public class TokenBankGameItemsController {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankGameItemsController.class);

    @Autowired
    TokenBankInGameAssetsService tokenBankInGameAssetsService;

    @GetMapping(path = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AllGameItemsResponse> getAllItemsForGame(@PathVariable String gameId) {
        logger.info("{} :: Received request to get all items for game: {}", TOKEN_BANK_PREPEND, gameId);
        HttpStatus httpStatus = null;
        AllGameItemsResponse response = null;
        try {
            response = tokenBankInGameAssetsService.getAllItemsForGame(gameId);
            httpStatus = HttpStatus.OK;
        } catch (Exception ex) {
            logger.error("{} Error getting all items for game {}: {}", TOKEN_BANK_PREPEND, gameId, ex.getMessage(), ex);
            response = new AllGameItemsResponse(STATUS_CODE_INTERNAL_ERROR, "Error getting items for game: " + ex.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddGameItemResponse> addGameItem(@RequestBody AddGameItemRequest addGameItemRequest) {
        logger.info("{} :: Received request to add game item for game: {} by user: {}", TOKEN_BANK_PREPEND, addGameItemRequest.getGameId(), addGameItemRequest.getUsername());
        AddGameItemResponse response = null;
        HttpStatus httpStatus = null;
        try {
            response = tokenBankInGameAssetsService.addGameItem(addGameItemRequest);
            httpStatus = HttpStatus.OK;
        } catch (Exception ex) {
            logger.error("{} Error adding game item for user {}: {}", TOKEN_BANK_PREPEND, addGameItemRequest.getUsername(), ex.getMessage(), ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping(path = "/unalive",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnAliveItemResponse> unAliveItem(@RequestBody UnAliveItemRequest unAliveItemRequest) {
        logger.info("{} :: Received request to unAlive item {} for game: {} by user: {}", TOKEN_BANK_PREPEND, unAliveItemRequest.getItemId(), unAliveItemRequest.getGameId(), unAliveItemRequest.getUsername());
        UnAliveItemResponse response = null;
        HttpStatus httpStatus = null;
        try {
            response = tokenBankInGameAssetsService.unAliveItem(unAliveItemRequest);
            httpStatus = HttpStatus.OK;
        } catch (Exception ex) {
            logger.error("{} Error unAliving item {} for user {}: {}", TOKEN_BANK_PREPEND, unAliveItemRequest.getItemId(), unAliveItemRequest.getUsername(), ex.getMessage(), ex);
            response = new UnAliveItemResponse(STATUS_CODE_INTERNAL_ERROR, "Error unAliving item: " + ex.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(response);
    }


}
