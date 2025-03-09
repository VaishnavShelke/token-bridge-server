package com.monolith.tokenmint.beans;

import lombok.Data;

@Data
public class ProviderInfo {

	private String providerId;
	private String chainId;
	private String chainName;
	private String websocketUrl;
	private String httpUrl;
	private String apiKey;
}
