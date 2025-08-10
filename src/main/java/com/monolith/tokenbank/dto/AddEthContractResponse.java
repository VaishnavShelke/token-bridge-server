package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.entities.ETHContractInfoEntity;
import lombok.Data;

@Data
public class AddEthContractResponse extends StandardResponseDto {
    private ETHContractInfoEntity ethContractInfoEntity;

    public AddEthContractResponse(String statusCode, String message) {
        super(statusCode, message);
    }

    public AddEthContractResponse(String statusCode, String message, ETHContractInfoEntity ethContractInfoEntity) {
        super(statusCode, message);
        this.ethContractInfoEntity = ethContractInfoEntity;
    }
} 