package com.monolith.tokenbank.service;

import com.monolith.tokenbank.helper.TokenBankUserCredsHelper;
import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserResponse;
import com.monolith.tokenbank.helper.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBankUserService {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankUserService.class);

    @Autowired
    private TokenBankUserCredsHelper tokenBankUserCredsHelper;

    public TokenBankCreateUserResponse createUser(TokenBankCreateUserRequest createUserRequest) {

        ValidationUtils.validateCreateUserRequest(createUserRequest);
        return tokenBankUserCredsHelper.createUser(createUserRequest);
    }
}
