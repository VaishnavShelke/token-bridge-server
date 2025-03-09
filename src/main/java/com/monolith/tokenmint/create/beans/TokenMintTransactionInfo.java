package com.monolith.tokenmint.create.beans;

import lombok.Data;

@Data
public class TokenMintTransactionInfo {

	private String tokenMintTransactionId;
	private String gameId;
	private String gameTransactionId;
	private String verifyAddressUrl;
	private String gameLandingPage;

	
}
