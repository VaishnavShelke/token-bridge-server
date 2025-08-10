package com.monolith.tokenbank.dto;

import com.monolith.tokenmint.entities.GameInfoEntity;
import lombok.Data;

@Data
public class OnBoardGameRequest extends TokenBankCreateUserRequest {
    private GameInfoEntity gameInfoEntity;
}
