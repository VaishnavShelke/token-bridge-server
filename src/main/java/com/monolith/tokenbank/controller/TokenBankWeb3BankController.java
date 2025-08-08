package com.monolith.tokenbank.controller;

import com.monolith.tokenbank.dto.AddEthContractRequest;
import com.monolith.tokenbank.dto.AddEthContractResponse;
import com.monolith.tokenbank.service.TokenBankWeb3BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.monolith.tokenbank.helper.TokenBankConstants.*;

@RestController
@RequestMapping("/tokenbank/web3bank")
public class TokenBankWeb3BankController {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankWeb3BankController.class);

    @Autowired
    private TokenBankWeb3BankService tokenBankWeb3BankService;

    @PostMapping(path = "/create-eth-contract", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddEthContractResponse> addMockEthContract(@RequestBody AddEthContractRequest request) {
        logger.info("{} :: Received request to add mock ETH contract for game: {}", TOKEN_BANK_PREPEND, request.getGameId());
        
        try {
            // Validate input
            if (request.getGameId() == null || request.getGameId().trim().isEmpty()) {
                logger.warn("{} :: Invalid gameId provided: {}", TOKEN_BANK_PREPEND, request.getGameId());
                AddEthContractResponse response = new AddEthContractResponse(STATUS_CODE_VALIDATION_FAILED, "GameId cannot be null or empty");
                return ResponseEntity.badRequest().body(response);
            }

            AddEthContractResponse response = tokenBankWeb3BankService.addEthContractInfo(request);
            
            if (STATUS_CODE_SUCCESS.equals(response.getStatusCode())) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
        } catch (Exception ex) {
            logger.error("{} :: Error adding mock ETH contract for game {}: {}", TOKEN_BANK_PREPEND, request.getGameId(), ex.getMessage(), ex);
            AddEthContractResponse response = new AddEthContractResponse(STATUS_CODE_INTERNAL_ERROR, "Error adding mock ETH contract: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
