package com.monolith.tokenmint.beans;

import com.monolith.tokenmint.entities.ETHContractInfoEntity;
import com.monolith.tokenmint.entities.GameInfoEntity;
import com.monolith.tokenmint.entities.GameItemsEntity;
import com.monolith.tokenmint.entities.ChainProviderInfoEntity;

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
	private GameInfoEntity gameInfoEntity;
	private ETHContractInfoEntity ethContractInfoEntity;
	private PlayerInfo palyerInfo;
	private GameItemsEntity gameItemsEntity;
	private OnChainTxnInfo onChainTxnInfo;
	private ChainProviderInfoEntity chainProviderInfoEntity;
}
