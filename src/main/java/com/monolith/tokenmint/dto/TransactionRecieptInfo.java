package com.monolith.tokenmint.dto;

import lombok.Data;

@Data
public class TransactionRecieptInfo {

	private String blockNumber;
	private String transactionHash;
	private String from;
}
