package com.monolith.tokenmint.entities;

import com.monolith.tokenmint.beans.ItemStatus;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "game_items")
public class ItemInfoBean {
	
	@Id
	@Column(name = "item_id")
	private String itemId;
	
	@Column(name = "game_id")
	private String gameId;
	
	@Column(name = "contract_item_id")
	private Integer contractItemId;
	
	@Column(name = "item_category")
	private String itemCategory;
	
	@Column(name = "item_title")
	private String itemTitle;
	
	@Column(name = "item_description", columnDefinition = "TEXT")
	private String itemDescription;
	
	@Column(name = "item_img_url")
	private String itemImgUrl;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "item_status")
	private ItemStatus itemStatus = ItemStatus.ALIVE; // Default value as per schema
	
	@Transient
	private String itemQuantity; // Runtime field for minting/transfer operations, not stored in DB
}
