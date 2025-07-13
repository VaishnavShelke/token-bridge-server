package com.monolith.tokenbank.dto;

import lombok.Data;

@Data
public class TokenBankCreateUserRequest {
    private String username;
    private String password;
    private String gameId;
    private String sessionMarker;
}
