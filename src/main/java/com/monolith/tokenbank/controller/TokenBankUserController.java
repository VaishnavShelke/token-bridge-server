package com.monolith.tokenbank.controller;

import com.monolith.tokenbank.dto.OnBoardGameRequest;
import com.monolith.tokenbank.dto.OnboardGameResponse;
import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserResponse;
import com.monolith.tokenbank.service.TokenBankUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.monolith.tokenbank.helper.TokenBankConstants.STATUS_CODE_INTERNAL_ERROR;
import static com.monolith.tokenbank.helper.TokenBankConstants.TOKEN_BANK_PREPEND;

@RestController
@RequestMapping("/tokenbank")
public class TokenBankUserController {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankUserController.class);

    @Autowired
    TokenBankUserService tokenBankUserService;

    @PostMapping(path = "/createuser")
    public ResponseEntity<TokenBankCreateUserResponse> createTokenBankUser(@RequestBody TokenBankCreateUserRequest createUserRequest){
        logger.info("{} :: Received create user request for username: {}",TOKEN_BANK_PREPEND, createUserRequest.getUsername());
        try {
            TokenBankCreateUserResponse response = tokenBankUserService.createUser(createUserRequest);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("{} Error creating user: {}",TOKEN_BANK_PREPEND, ex.getMessage(), ex);
            TokenBankCreateUserResponse response = new TokenBankCreateUserResponse(STATUS_CODE_INTERNAL_ERROR,"Error creating user"+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping(path = "/onboard-game")
    public ResponseEntity<OnboardGameResponse> createTokenBankUser(@RequestBody OnBoardGameRequest onBoardGameRequest){
        logger.info("{} :: Received onboard game request : {}",TOKEN_BANK_PREPEND, onBoardGameRequest.getUsername());
        try {
            OnboardGameResponse response = tokenBankUserService.onboardGame(onBoardGameRequest);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("{} Error onboarding game: {}",TOKEN_BANK_PREPEND, ex.getMessage(), ex);
            OnboardGameResponse response = new OnboardGameResponse(STATUS_CODE_INTERNAL_ERROR,"Error onboarding game"+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping(path = "/onboard-game/edit")
    public ResponseEntity<OnboardGameResponse> editTokenBankUser(@RequestBody OnBoardGameRequest onBoardGameRequest){
        logger.info("{} :: Received edit game request : {}",TOKEN_BANK_PREPEND, onBoardGameRequest.getUsername());
        try {
            OnboardGameResponse response = tokenBankUserService.editGame(onBoardGameRequest);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error("{} Error in edit game: {}",TOKEN_BANK_PREPEND, ex.getMessage(), ex);
            OnboardGameResponse response = new OnboardGameResponse(STATUS_CODE_INTERNAL_ERROR,"Error in editing game"+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
