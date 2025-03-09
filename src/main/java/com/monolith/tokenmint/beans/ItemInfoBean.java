package com.monolith.tokenmint.beans;

import lombok.Data;

@Data
public class ItemInfoBean {
	private String itemId;
	private String itemCategory;
	private String itemTitle;
	private String itemDescription;
	private String itemImgUrl;
	private String itemQuantity;
	private String itemPrice;
}
