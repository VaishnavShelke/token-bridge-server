package com.monolith.shared.utils;

public class GameServerConstants {

	public static String RESPONSE_CODE_SUCCESS="000";
	public static String RESPONSE_CODE_FAILED = "001";
	
	
	public static enum GameServerEndpoint{
		SUBMIT_VERIFIED_ADDRESS("submitVerifiedAddress"),
		SUMBIT_TOKEN_TRANSFERRED_STATUS("submitTokenTransferredStatus");
		String val;
		GameServerEndpoint(String val){
			this.val = val;
		}
		public String getValue() {return this.val;}
	}
}
