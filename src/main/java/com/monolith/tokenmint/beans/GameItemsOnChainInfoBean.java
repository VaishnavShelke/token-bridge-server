package com.monolith.tokenmint.beans;

import lombok.Data;

@Data
public class GameItemsOnChainInfoBean {
	private String gameId;
	private String itemId;
	private String itemContractId;
	private String tokenIssuerAddress;
	private String itemCreationDate;
}
