package com.monolith.tokenmint.beans;

import lombok.Data;

@Data
public class ETHContractInfo {

	private String gameId;
	private String ethContractId;
	private String chain;
	private String chainId;
	private String chainCurrency;
	private String contractName;
	private String operatorId;
	private String contractAddress;
	private String contractAbi;
	private String updatedBy;
	private String updatedOn;
	private String etherscanContractUrl;
	private String providerId;
	
}
