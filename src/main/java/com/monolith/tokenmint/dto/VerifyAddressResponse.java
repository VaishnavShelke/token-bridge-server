package com.monolith.tokenmint.dto;

import com.monolith.tokenmint.beans.CommonDTOFields;

import lombok.Data;

@Data
public class VerifyAddressResponse extends CommonDTOFields {

	private String tokenMintTransactionId;
	private String address;
	private String addressVerified;
	private String addressEtherScanUrl;
	
	public VerifyAddressResponse setStatusCodeDesc(String statusCode,String statusDesc) {
		setStatusCode(statusCode);
		setStatusDescription(statusDesc);
		return this;
	}
}
