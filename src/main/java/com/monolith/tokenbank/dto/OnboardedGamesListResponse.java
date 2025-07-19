package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.beans.GameInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OnboardedGamesListResponse extends StandardResponseDto {
    public OnboardedGamesListResponse(String statusCode, String message) {
        super(statusCode, message);
    }

    private List<GameInfo> gamesList;
} 