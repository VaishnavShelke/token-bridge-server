package com.monolith.shared.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.monolith.shared.utils.GameServerConstants;
import com.monolith.shared.utils.TokenMintConstants;
import com.monolith.shared.utils.Utility;
import com.monolith.shared.utils.Web3GatewayConstants;
import com.monolith.tokenmint.create.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.create.dto.Web3GtwTransferTokenRequest;
import com.monolith.tokenmint.ui.dto.VerifyAddressRequest;
import com.monolith.tokenmint.ui.dto.VerifyAddressResponse;

@Service
public class ConnectWeb3Gateway {
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectWeb3Gateway.class);

	@Value("${internal.verify-address-url}")
	private String verifyAddressUrl;
	
	
	@Value("${internal.transfer-tokens-url}")
	private String transferTokensUrl;
	
	@Autowired
	HttpConnectService httpConnectService;
	
	public VerifyAddressResponse coneectWeb3AndVerifyAddress(VerifyAddressRequest verifyAddressRequest,VerifyAddressResponse verifyAddressResponse,CreateTokenTransactionBean createTokenTransactionBean) {
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("message", createTokenTransactionBean.getOtp());
		jsonObject.addProperty("signedMessage", verifyAddressRequest.getSignedMessage());
		jsonObject.addProperty("address", verifyAddressRequest.getAddress());
		String verifySignerJson = gson.toJson(jsonObject);
		logger.info("verifySignerRequest :: {}",verifySignerJson);
		
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("Accept", "application/json");
		headerMap.put("Content-type", "application/json");
		String response = httpConnectService.postRequest(headerMap, verifySignerJson, verifyAddressUrl);
		
		JsonObject respJson = gson.fromJson(response, JsonObject.class);
		if(respJson != null) {
			logger.info("statusCode Code :: {} <-> statusDescription :: {} ",respJson.get("statusCode"),respJson.get("statusDesc"));
			if(respJson.get("statusCode") != null 
					&& !respJson.get("statusCode").isJsonNull()
						&& Web3GatewayConstants.RESPONSE_CODE_SUCCESS.equals(respJson.get("statusCode").getAsString())) {
				logger.info("web3 gateway message verified v`v`");
				verifyAddressResponse.setAddressVerified("Y");
				verifyAddressResponse.setAddress(verifyAddressRequest.getAddress());
			}else {
				logger.warn("web3 gateway message not verified v`v`");
				verifyAddressResponse.setAddressVerified("N");
				verifyAddressResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_FALIURE, "Address Not Verified");
			}
		}else {
			logger.error("Response from web3 gateway is null");
			verifyAddressResponse.setAddressVerified("N");
			verifyAddressResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_INTERNAL_SERVER_ERROR, "Error Response from Node Web3 Gateway");
		}
		return verifyAddressResponse;
	}
	
	public Object transferTokensRequest(CreateTokenTransactionBean cttbean,Web3GtwTransferTokenRequest transferTokenRequest, VerifyAddressResponse verifyAddressResponse) {
		Gson gson = new Gson();
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("Accept", "application/json");
		headerMap.put("Content-type", "application/json");
		String transferTokenRequestJson = Utility.getJsonFromObject(transferTokenRequest);
		logger.info("Web3GtwTransferTokenRequest Request {}",Utility.getJsonFromObject(transferTokenRequestJson));
		String response = httpConnectService.postRequest(headerMap, transferTokenRequestJson, transferTokensUrl);
		
		if(response == null) {
			logger.error("Transfer Token Request Failed Web3 Gateway DOWN");
			verifyAddressResponse.setStatusCode(TokenMintConstants.RESPONSE_CODE_FALIURE);
			verifyAddressResponse.setStatusDescription("Null Response from WEB 3 Gateway");
		}else {
			JsonObject respJson = null;
			try{
				respJson = gson.fromJson(response, JsonObject.class);
			}catch (Exception ex){
				logger.info("Exception in parsing response of transfer token event : {}",ex.getMessage());
			}

			if(respJson != null && respJson.get("statusCode") != null && !respJson.get("statusCode").isJsonNull() && "000".equals(respJson.get("statusCode").getAsString())) {
				verifyAddressResponse.setStatusCode(TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE);
			}else {
				verifyAddressResponse.setStatusCode(TokenMintConstants.RESPONSE_CODE_FALIURE);
				verifyAddressResponse.setStatusDescription("Invalid Response from WEB 3 Gateway");
				logger.error("Transfer Token Request Failed Web3 Gateway DOWN");
			}
		}
		
		
		return null;
	}
}
