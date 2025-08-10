package com.monolith.tokenmint.dto;

import lombok.Data;

@Data
public class TransferEventPacketIn {

		private String tokenMintTransactionId;
		private String operator;
		private String from;
		private String to;
		private String id;
		private String value;
	private TransactionRecieptInfo transactionReciept; 
	
}
