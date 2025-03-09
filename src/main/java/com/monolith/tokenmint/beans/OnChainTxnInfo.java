package com.monolith.tokenmint.beans;

import com.monolith.tokenmint.create.dto.Web3GtwTransferTokenRequest;

import lombok.Data;


@Data
public class OnChainTxnInfo extends Web3GtwTransferTokenRequest{

	private String txnSuccessfull = "N";
	private String onChainDateTime;
	private String blockNumber;
	private String transactionHash;
}
