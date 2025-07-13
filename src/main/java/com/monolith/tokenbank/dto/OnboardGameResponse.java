package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.beans.GameInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnboardGameResponse extends  StandardResponseDto{
    public OnboardGameResponse(String statusCode, String message) {
        super(statusCode, message);
    }

    private GameInfo gameInfo;
}
