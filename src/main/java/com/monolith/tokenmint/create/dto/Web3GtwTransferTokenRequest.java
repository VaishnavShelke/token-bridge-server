package com.monolith.tokenmint.create.dto;

import lombok.Data;

@Data
public class Web3GtwTransferTokenRequest {
	private String providerId;
	private String tokenMintTransactionId;
	private String contractId;
	private String contractName;
	private String contractAddress;
	private String contractChainId;
	private String contractABI;
	private String contractOperatorAddress;
	private Web3ContractArguments web3ContractArguments;
}

