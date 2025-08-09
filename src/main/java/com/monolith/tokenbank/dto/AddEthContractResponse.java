package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.entities.ETHContractInfo;
import lombok.Data;

@Data
public class AddEthContractResponse extends StandardResponseDto {
    private ETHContractInfo ethContractInfo;

    public AddEthContractResponse(String statusCode, String message) {
        super(statusCode, message);
    }

    public AddEthContractResponse(String statusCode, String message, ETHContractInfo ethContractInfo) {
        super(statusCode, message);
        this.ethContractInfo = ethContractInfo;
    }
} 