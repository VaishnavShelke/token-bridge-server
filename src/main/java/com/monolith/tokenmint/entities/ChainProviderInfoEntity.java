package com.monolith.tokenmint.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "chain_provider_info")
public class ChainProviderInfoEntity {

	@Id
	@Column(name = "provider_id")
	private String providerId;
	
	@Column(name = "chain_id")
	private String chainId;
	
	@Column(name = "chain_name")
	private String chainName;
	
	@Column(name = "websocket_url")
	private String websocketUrl;
	
	@Column(name = "http_url")
	private String httpUrl;
	
	@Column(name = "api_key")
	private String apiKey;
}
