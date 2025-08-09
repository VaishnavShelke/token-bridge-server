package com.monolith.tokenbank.service;

import com.monolith.shared.dao.EthContractInfoDAO;
import com.monolith.tokenbank.dto.AddEthContractRequest;
import com.monolith.tokenbank.dto.AddEthContractResponse;
import com.monolith.tokenmint.beans.ETHContractInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.monolith.tokenbank.helper.TokenBankConstants.*;

@Service
public class TokenBankWeb3BankService {

    private static final Logger logger = LoggerFactory.getLogger(TokenBankWeb3BankService.class);

    @Autowired
    private EthContractInfoDAO ethContractInfoDAO;

    public AddEthContractResponse addEthContractInfo(AddEthContractRequest request) {
        logger.info("{} :: Creating mock ETH contract info for game: {}", TOKEN_BANK_PREPEND, request.getGameId());

        try {
            // Create mock ETH contract info
            ETHContractInfo mockEthContractInfo = createMockEthContractInfo(request.getGameId());

            // Insert into database
            boolean success = ethContractInfoDAO.insertEthContractInfo(mockEthContractInfo);
            if (success) {
                logger.info("{} :: Successfully created mock ETH contract info for game: {}", TOKEN_BANK_PREPEND, request.getGameId());
                return new AddEthContractResponse(STATUS_CODE_SUCCESS, "Mock ETH contract info created successfully", mockEthContractInfo);
            } else {
                logger.error("{} :: Failed to insert mock ETH contract info for game: {}", TOKEN_BANK_PREPEND, request.getGameId());
                return new AddEthContractResponse(STATUS_CODE_INTERNAL_ERROR, "Failed to create mock ETH contract info");
            }

        } catch (Exception e) {
            logger.error("{} :: Error creating mock ETH contract info for game: {} - {}", TOKEN_BANK_PREPEND, request.getGameId(), e.getMessage(), e);
            return new AddEthContractResponse(STATUS_CODE_INTERNAL_ERROR, "Error creating mock ETH contract info: " + e.getMessage());
        }
    }

    private ETHContractInfo createMockEthContractInfo(String gameId) {
        ETHContractInfo mockInfo = new ETHContractInfo();
        
        String contractId = "mock-contract-" + UUID.randomUUID().toString().substring(0, 8);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        mockInfo.setGameId(gameId);
        mockInfo.setEthContractId(contractId);
        mockInfo.setChain("ethereum");
        mockInfo.setChainId("1");
        mockInfo.setContractName("Mock Token Contract for " + gameId);
        mockInfo.setOperatorId("mock-operator-001");
        mockInfo.setContractAddress("0x" + UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        mockInfo.setContractAbi("[{\"constant\":true,\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"type\":\"function\"}]");
        mockInfo.setUpdatedBy("system");
        mockInfo.setUpdatedOn(timestamp);
        mockInfo.setEtherscanContractUrl("https://etherscan.io/address/" + mockInfo.getContractAddress());
        mockInfo.setProviderId("mock-provider-001");
        
        return mockInfo;
    }
} 