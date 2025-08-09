package com.monolith.tokenmint.ui.dto;

import com.monolith.tokenmint.beans.CommonDTOFields;
import com.monolith.tokenmint.entities.ETHContractInfo;
import com.monolith.tokenmint.entities.ItemInfoBean;
import com.monolith.tokenmint.beans.PlayerInfo;
import com.monolith.tokenmint.create.beans.TokenMintTransactionInfo;
import com.monolith.tokentrade.TokenMintUserInfo;

import lombok.Data;

@Data
public class LoadUIDTOResponse extends CommonDTOFields{

	private String tokenMintTransactionId;
	private PlayerInfo playerInfo;
	private TokenMintTransactionInfo tokenMintTransactionInfo;
	private TokenMintUserInfo tokenMintUserInfo;
	private ItemInfoBean itemInfo;
	private ETHContractInfo contractInfo;
	
	public LoadUIDTOResponse setStatusCodeDesc(String statusCode,String statusDesc) {
		setStatusCode(statusCode);
		setStatusDescription(statusDesc);
		return this;
	}
}
