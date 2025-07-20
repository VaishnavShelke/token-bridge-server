package com.monolith.tokenbank.service;

import com.monolith.tokenbank.helper.GameInfoHelper;
import com.monolith.tokenbank.helper.TokenBankUserCredsHelper;
import com.monolith.tokenbank.dto.AllGamesResponse;
import com.monolith.tokenbank.dto.OnBoardGameRequest;
import com.monolith.tokenbank.dto.OnboardGameResponse;
import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserResponse;
import com.monolith.tokenbank.helper.TokenBankConstants;
import com.monolith.tokenbank.helper.ValidationUtils;
import com.monolith.tokenmint.beans.GameInfo;
import com.monolith.tokenmint.create.dao.GameInfoDAO;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.monolith.tokenbank.helper.TokenBankConstants.*;
import static com.monolith.tokenbank.helper.TokenBankConstants.STATUS_CODE_VALIDATION_FAILED;

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
