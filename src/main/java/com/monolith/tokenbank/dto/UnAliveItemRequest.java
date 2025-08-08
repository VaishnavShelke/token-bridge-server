package com.monolith.tokenbank.dto;

import lombok.Data;

@Data
public class UnAliveItemRequest {
    private String username;
    private String gameId;
    private String itemId;
} 