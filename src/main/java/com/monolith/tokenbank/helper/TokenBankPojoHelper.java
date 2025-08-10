package com.monolith.tokenbank.helper;


import com.monolith.tokenbank.entities.TokenBankUserCredsEntity;
import com.monolith.tokenbank.dto.AddGameItemRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenmint.entities.GameInfoEntity;
import com.monolith.tokenmint.entities.GameItemsEntity;
import com.monolith.tokenmint.beans.ItemStatus;

public class TokenBankPojoHelper {

    public static void getItemInfoBeanFromAddGameItemRequest(GameItemsEntity itemInfo, AddGameItemRequest request, String generatedItemId){
        itemInfo.setGameId(request.getGameId());
        itemInfo.setItemId(generatedItemId); // Use the generated ID instead of request.getItemId()
        itemInfo.setContractItemId(request.getContractItemId());
        itemInfo.setItemCategory(request.getItemCategory());
        itemInfo.setItemTitle(request.getItemTitle());
        itemInfo.setItemDescription(request.getItemDescription());
        itemInfo.setItemImgUrl(request.getItemImgUrl());
        itemInfo.setItemStatus(request.getItemStatus() != null ? request.getItemStatus() : ItemStatus.ALIVE);
    }

    public static void getGameInfoFromOnBoardGameRequest(GameInfoEntity gameInfoEntity, GameInfoEntity requestGameInfoEntity) {
        gameInfoEntity.setGameName(requestGameInfoEntity.getGameName());
        gameInfoEntity.setGameParentCompany(requestGameInfoEntity.getGameParentCompany());
        gameInfoEntity.setGameLogo(requestGameInfoEntity.getGameLogo());
        gameInfoEntity.setApiKey(TokenBankUtil.generateApiKey());
    }

    public static void setGameInfoFromOnBoardGameRequest(GameInfoEntity currentGameInfoEntity, GameInfoEntity requestGameInfoEntity) {
        currentGameInfoEntity.setGameName(requestGameInfoEntity.getGameName());
        currentGameInfoEntity.setGameParentCompany(requestGameInfoEntity.getGameParentCompany());
        currentGameInfoEntity.setGameLogo(requestGameInfoEntity.getGameLogo());
    }

    public static void setTokenBankUserCredsFromTokenBankCreateUserRequest(TokenBankUserCredsEntity userCreds, TokenBankCreateUserRequest createUserRequest) {
        userCreds.setUsername(createUserRequest.getUsername());
        userCreds.setPassword(createUserRequest.getPassword());
        userCreds.setGameId(TokenBankConstants.DEFAULT_GAME_ID);
        userCreds.setUserRole(TokenBankConstants.DEFAULT_USER_ROLE);
    }
}
