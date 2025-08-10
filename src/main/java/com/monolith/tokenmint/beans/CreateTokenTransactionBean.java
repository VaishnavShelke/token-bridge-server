package com.monolith.tokenmint.beans;

import com.monolith.tokenmint.entities.ETHContractInfo;
import com.monolith.tokenmint.entities.GameInfo;
import com.monolith.tokenmint.entities.ItemInfoBean;
import com.monolith.tokenmint.entities.ProviderInfo;

import lombok.Data;

@Data
public class CreateTokenTransactionBean {

	private String tokenMintTransactionId;
	private String tokenMintTransactionIdHex;
	private String gameId;
	private String gameTransactionId;
	private String otp;
	private String tableName;
	private String recieptientAddress;
	private String gameLandingPage;
	private GameInfo gameInfo;
	private ETHContractInfo ethContractInfo;
	private PlayerInfo palyerInfo;
	private ItemInfoBean itemInfoBean;
	private OnChainTxnInfo onChainTxnInfo;
	private ProviderInfo providerInfo;
}
