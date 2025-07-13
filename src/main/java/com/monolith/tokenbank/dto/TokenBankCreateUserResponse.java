package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.create.dto.CommonTransDTOFields;
import lombok.Data;
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
