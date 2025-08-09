package com.monolith.tokenmint.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "eth_contract_info")
@IdClass(ETHContractInfoId.class)
public class ETHContractInfo {

	@Id
	@Column(name = "game_id")
	private String gameId;
	
	@Id
	@Column(name = "eth_contract_id")
	private String ethContractId;
	
	@Column(name = "chain")
	private String chain;
	
	@Column(name = "chain_id")
	private String chainId;
	
	@Column(name = "chain_currency")
	private String chainCurrency;
	
	@Column(name = "contract_name")
	private String contractName;
	
	@Column(name = "operator_id")
	private String operatorId;
	
	@Column(name = "contract_address")
	private String contractAddress;
	
	@Column(name = "contract_abi", columnDefinition = "TEXT")
	private String contractAbi;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_on")
	private String updatedOn;
	
	@Column(name = "etherscan_contract_url")
	private String etherscanContractUrl;
	
	@Column(name = "provider_id")
	private String providerId;
	
}
