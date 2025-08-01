package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.beans.GameInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllGamesResponse extends StandardResponseDto {
    public AllGamesResponse(String statusCode, String message) {
        super(statusCode, message);
    }

    private List<GameInfo> games;
} 