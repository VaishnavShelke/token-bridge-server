package com.monolith.tokenmint.beans;

import lombok.Data;

@Data
public class ItemInfoBean {
	private String gameId;
	private String itemId;
	private Integer contractItemId;
	private String itemCategory;
	private String itemTitle;
	private String itemDescription;
	private String itemImgUrl;
	private ItemStatus itemStatus = ItemStatus.ALIVE; // Default value as per schema
	private String itemQuantity; // Runtime field for minting/transfer operations, not stored in DB
}
