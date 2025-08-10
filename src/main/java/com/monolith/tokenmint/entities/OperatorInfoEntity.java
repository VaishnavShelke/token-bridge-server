package com.monolith.tokenmint.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "operator_info")
public class OperatorInfoEntity {

	@Id
	@Column(name = "operator_id")
	private String operatorId;
	
	@Column(name = "operator_name")
	private String operatorName;
	
	@Column(name = "operator_address")
	private String operatorAddress;
	
	@Column(name = "eth_contract_id")
	private String ethContractId;
}
