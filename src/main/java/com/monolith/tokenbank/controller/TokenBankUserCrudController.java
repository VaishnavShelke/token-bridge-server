package com.monolith.tokenbank.controller;

import com.monolith.tokenbank.dto.AllGamesResponse;
import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserResponse;
import com.monolith.tokenbank.service.TokenBankUserService;
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
@RequestMapping("/tokenbank/user")
public class TokenBankUserCrudController {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankUserCrudController.class);

    @Autowired
    TokenBankUserService tokenBankUserService;

    @PostMapping(path = "", 
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenBankCreateUserResponse> createTokenBankUser(@RequestBody TokenBankCreateUserRequest createUserRequest) {
        logger.info("{} :: Received create user request for username: {}", TOKEN_BANK_PREPEND, createUserRequest.getUsername());
        
        // Input validation
        if (createUserRequest.getUsername() == null || createUserRequest.getUsername().trim().isEmpty()) {
            logger.warn("{} :: Username is required for user creation", TOKEN_BANK_PREPEND);
            TokenBankCreateUserResponse response = new TokenBankCreateUserResponse(STATUS_CODE_VALIDATION_FAILED, "Username is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (createUserRequest.getPassword() == null || createUserRequest.getPassword().trim().isEmpty()) {
            logger.warn("{} :: Password is required for user creation", TOKEN_BANK_PREPEND);
            TokenBankCreateUserResponse response = new TokenBankCreateUserResponse(STATUS_CODE_VALIDATION_FAILED, "Password is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            TokenBankCreateUserResponse response = tokenBankUserService.createUser(createUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            logger.error("{} Error creating user: {}", TOKEN_BANK_PREPEND, ex.getMessage(), ex);
            TokenBankCreateUserResponse response = new TokenBankCreateUserResponse(STATUS_CODE_INTERNAL_ERROR, "Error creating user: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
