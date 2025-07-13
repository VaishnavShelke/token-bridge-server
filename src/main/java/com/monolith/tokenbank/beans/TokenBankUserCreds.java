package com.monolith.tokenbank.beans;

import lombok.Data;

@Data
public class TokenBankUserCreds {
    private String username;
    private String gameId;
    private String password;
    private String userRole;
}
