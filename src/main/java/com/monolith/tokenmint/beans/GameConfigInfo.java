package com.monolith.tokenmint.beans;

import java.util.HashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.monolith.shared.sqldb.SQLDataSourceInfo;

import lombok.Data;


@Data
public class GameConfigInfo {
	private static final Logger logger = LoggerFactory.getLogger(GameConfigInfo.class);

	public static final String GAMESERVER_ENDPOINTS = "ENDPOINT";
	
	private String gameId;
	private String configType;
	private String configValue;
	private String updatedBy;
	private String updatedOn;
	
	private HashMap<String,String> endpointUrls = null;
	private SQLDataSourceInfo sqlDataSourceInfo = null;
	
	public HashMap<String,String> getParsedEndpointUrlMap(){
		try {
			if(endpointUrls != null) {
				return endpointUrls;
			}else {
				logger.info("PARSING :: json --> EndpointUrlMap");
				Gson gson = new Gson();
				JsonObject jsonObject=gson.fromJson(configValue,JsonObject.class);
				endpointUrls = new HashMap<>();
				for(Entry<String,JsonElement> epurl : jsonObject.entrySet()) {
					endpointUrls.put(epurl.getKey(),epurl.getValue().getAsString());
				}
				return endpointUrls;
			}
		}catch (Exception e) {
			logger.error("Error In Parsing {}",e.getMessage());
			return null;
		}
		
	}
}
