package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.entities.GameInfo;
import lombok.Data;

@Data
public class OnBoardGameRequest extends TokenBankCreateUserRequest {
    private GameInfo gameInfo;
}
