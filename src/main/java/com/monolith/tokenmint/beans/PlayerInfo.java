package com.monolith.tokenmint.beans;

import java.util.List;

import lombok.Data;

@Data
public class PlayerInfo {

	/*
	 * playerUserId == unique Id by which the game identifies the player
	 */
	private String playerId;
	
	/*
	 * Common Id across all games
	 */
	private String tokenMintUserId;
	
	/*
	 * playerUserName == existing player name
	 */
	private String playerName;
	
	/*
	 * profilePicture of the player
	 */
	private String playerProfilePic;
	
	/*
	 * playerEthAccounts == ETH accounts holding player assets for the game
	 */
	private List<String> playerEthAccounts;
	
	private String gameLogo;
	
	private String gameName;
}
