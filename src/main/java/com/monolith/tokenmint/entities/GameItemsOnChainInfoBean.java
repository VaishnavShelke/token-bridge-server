package com.monolith.tokenmint.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "game_items_on_chain_info")
@IdClass(GameItemsOnChainInfoId.class)
public class GameItemsOnChainInfoBean {
	
	@Id
	@Column(name = "game_id")
	private String gameId;
	
	@Id
	@Column(name = "item_id")
	private String itemId;
	
	@Column(name = "item_contract_id")
	private String itemContractId;
	
	@Column(name = "token_issuer_address")
	private String tokenIssuerAddress;
	
	@Column(name = "item_creation_date")
	private String itemCreationDate;
}
