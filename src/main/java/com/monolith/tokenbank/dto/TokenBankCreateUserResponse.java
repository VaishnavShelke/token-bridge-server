package com.monolith.tokenbank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenBankCreateUserResponse extends StandardResponseDto {

    private String username;

    public TokenBankCreateUserResponse(String statusCode, String message) {
        super(statusCode, message);
    }
}
