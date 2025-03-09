package com.monolith.tokenmint.create.beans;

import com.monolith.tokenmint.beans.ETHContractInfo;
import com.monolith.tokenmint.beans.GameInfo;
import com.monolith.tokenmint.beans.ItemInfoBean;
import com.monolith.tokenmint.beans.OnChainTxnInfo;
import com.monolith.tokenmint.beans.PlayerInfo;
import com.monolith.tokenmint.beans.ProviderInfo;

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
