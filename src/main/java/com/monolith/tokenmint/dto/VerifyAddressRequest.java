package com.monolith.tokenmint.dto;

import lombok.Data;

@Data
public class VerifyAddressRequest {
	
	private String tokenMintTransactionId;
	private String signedMessage;
	private String address;

}
