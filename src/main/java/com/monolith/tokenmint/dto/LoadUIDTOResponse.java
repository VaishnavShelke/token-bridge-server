package com.monolith.tokenmint.dto;

import com.monolith.tokenmint.beans.CommonDTOFields;
import com.monolith.tokenmint.entities.ETHContractInfoEntity;
import com.monolith.tokenmint.entities.GameItemsEntity;
import com.monolith.tokenmint.beans.PlayerInfo;
import com.monolith.tokenmint.beans.TokenMintTransactionInfo;
import com.monolith.tokentrade.TokenMintUserInfo;

import lombok.Data;

@Data
public class LoadUIDTOResponse extends CommonDTOFields{

	private String tokenMintTransactionId;
	private PlayerInfo playerInfo;
	private TokenMintTransactionInfo tokenMintTransactionInfo;
	private TokenMintUserInfo tokenMintUserInfo;
	private GameItemsEntity itemInfo;
	private ETHContractInfoEntity contractInfo;
	
	public LoadUIDTOResponse setStatusCodeDesc(String statusCode,String statusDesc) {
		setStatusCode(statusCode);
		setStatusDescription(statusDesc);
		return this;
	}
}
