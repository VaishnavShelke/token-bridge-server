package com.monolith.shared.redis;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RedisDataSourceInfo {
	@JsonProperty("clusterNodes")
	private List<String> clusterNodes;
	
	@JsonProperty("maxRedirects")
	private String maxRedirects;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("hostname")
	private String hostname;
	
	@JsonProperty("port")
	private String port;
	
}
