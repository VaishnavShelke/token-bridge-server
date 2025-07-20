package com.monolith.tokenbank.helper;


import com.monolith.tokenbank.beans.TokenBankUserCreds;
import com.monolith.tokenbank.dto.AddGameItemRequest;
import com.monolith.tokenbank.dto.TokenBankCreateUserRequest;
import com.monolith.tokenmint.beans.GameInfo;
import com.monolith.tokenmint.beans.ItemInfoBean;
import com.monolith.tokenmint.beans.ItemStatus;

public class TokenBankPojoHelper {

    public static void getItemInfoBeanFromAddGameItemRequest(ItemInfoBean itemInfo, AddGameItemRequest request, String generatedItemId){
        itemInfo.setGameId(request.getGameId());
        itemInfo.setItemId(generatedItemId); // Use the generated ID instead of request.getItemId()
        itemInfo.setContractItemId(request.getContractItemId());
        itemInfo.setItemCategory(request.getItemCategory());
        itemInfo.setItemTitle(request.getItemTitle());
        itemInfo.setItemDescription(request.getItemDescription());
        itemInfo.setItemImgUrl(request.getItemImgUrl());
        itemInfo.setItemStatus(request.getItemStatus() != null ? request.getItemStatus() : ItemStatus.ALIVE);
    }

    public static void getGameInfoFromOnBoardGameRequest(GameInfo gameInfo, GameInfo requestGameInfo) {
        gameInfo.setGameName(requestGameInfo.getGameName());
        gameInfo.setGameParentCompany(requestGameInfo.getGameParentCompany());
        gameInfo.setGameLogo(requestGameInfo.getGameLogo());
        gameInfo.setApiKey(TokenBankUtil.generateApiKey());
    }

    public static void setGameInfoFromOnBoardGameRequest(GameInfo currentGameInfo, GameInfo requestGameInfo) {
        currentGameInfo.setGameName(requestGameInfo.getGameName());
        currentGameInfo.setGameParentCompany(requestGameInfo.getGameParentCompany());
        currentGameInfo.setGameLogo(requestGameInfo.getGameLogo());
    }

    public static void setTokenBankUserCredsFromTokenBankCreateUserRequest(TokenBankUserCreds userCreds, TokenBankCreateUserRequest createUserRequest) {
        userCreds.setUsername(createUserRequest.getUsername());
        userCreds.setPassword(createUserRequest.getPassword());
        userCreds.setGameId(TokenBankConstants.DEFAULT_GAME_ID);
        userCreds.setUserRole(TokenBankConstants.DEFAULT_USER_ROLE);
    }
}
