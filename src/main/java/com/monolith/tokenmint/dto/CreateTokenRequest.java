package com.monolith.tokenmint.dto;

import java.util.List;

import com.monolith.tokenmint.entities.ItemInfoBean;
import com.monolith.tokenmint.beans.PlayerInfo;

import lombok.Data;

@Data
public class CreateTokenRequest extends CommonTransDTOFields{
	/*
	 * gameItemIds == Item info for game
	 */
	private ItemInfoBean gameItemInfo;
	
	/*
	 * userGameId == uniqueId for user identification
	 */
	private PlayerInfo playerInfo;
	
	/*
	 * registeredEthAddress == address that are registered and stored with the game
	 */
	private List<String> registeredEthAddress;
	
	private String gameLandingPage;

	private String otp;
}

