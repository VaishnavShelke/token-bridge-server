package com.monolith.tokenmint.create.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.monolith.shared.dao.GameConfigDAO;
import com.monolith.shared.service.HttpConnectService;
import com.monolith.shared.utils.GameServerConstants;
import com.monolith.shared.utils.GameServerConstants.GameServerEndpoint;
import com.monolith.shared.utils.TokenMintConstants;
import com.monolith.tokenmint.beans.OnChainTxnInfo;
import com.monolith.tokenmint.create.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.ui.dto.VerifyAddressResponse;

@Service
public class GameServerInteractionHandler {

	
	private static final Logger logger = LoggerFactory.getLogger(GameServerInteractionHandler.class);
	
	@Autowired
	private GameConfigDAO gameConfigDAO;
	
	@Autowired
	private HttpConnectService httpConnectService;
	
	@Value("${game-token-transfer-event-listener-url}")
	private String gameTokensTransferEventListenerUrl;
	
	public VerifyAddressResponse sendAddressVerifedToGameServers(VerifyAddressResponse verifyAddressResponse,
			CreateTokenTransactionBean createTokenTransactionBean) {
		
		String gameId = createTokenTransactionBean.getGameId();
		String submitVerifiedAddressUrl = gameConfigDAO.getGameServerEndpointURL(gameId,GameServerEndpoint.SUBMIT_VERIFIED_ADDRESS);
		
		Gson gson = new Gson();
		JsonObject jsonRequest = new JsonObject();
		jsonRequest.addProperty("tokenMintTransactionId", verifyAddressResponse.getTokenMintTransactionId());
		jsonRequest.addProperty("gameTransactionId", createTokenTransactionBean.getGameTransactionId());
		jsonRequest.addProperty("recipientAddress", verifyAddressResponse.getAddress());
		jsonRequest.addProperty("verificationStatus", verifyAddressResponse.getAddressVerified());
		String jsonVerificationAckn = gson.toJson(jsonRequest);
		
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("Accept", "application/json");
		headerMap.put("Content-type", "application/json");
		String gsResponseJson = httpConnectService.postRequest(headerMap, jsonVerificationAckn, submitVerifiedAddressUrl);
		gsResponseJson = "{\"statusCode\" : \"000\"}";
		logger.info("HARDCODED RESPONSE :: {}",gsResponseJson);
		JsonObject jsonGsResponse = gson.fromJson(gsResponseJson, JsonObject.class);
		String gsStatusDesc = "Acknoweldgement Rejected By Game Servers";
		if(jsonGsResponse != null) {
			if(jsonGsResponse.get("statusCode") != null && !jsonGsResponse.get("statusCode").isJsonNull()) {
				gsStatusDesc = jsonGsResponse.get("statusCode").getAsString();
			}
			if(jsonGsResponse.get("statusCode") != null && !jsonGsResponse.get("statusCode").isJsonNull()) {
				String statusCode = jsonGsResponse.get("statusCode").getAsString();
				if(GameServerConstants.RESPONSE_CODE_SUCCESS.equals(statusCode)) {
					verifyAddressResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE,"Game Server Acknoweldged the request");
				}else {
					verifyAddressResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_FALIURE,gsStatusDesc);
				}
			}else {
				verifyAddressResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_FALIURE,gsStatusDesc);
			}
		}else {
			verifyAddressResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_FALIURE,gsStatusDesc);
		}
		return verifyAddressResponse;
	}

	public void sendEthTokenTransferStatus(CreateTokenTransactionBean createTokenTransactionBean) {
		
		String gameId = createTokenTransactionBean.getGameId();
		String submitEthTokensTransferred = gameConfigDAO.getGameServerEndpointURL(gameId,GameServerEndpoint.SUMBIT_TOKEN_TRANSFERRED_STATUS);
		OnChainTxnInfo octi = createTokenTransactionBean.getOnChainTxnInfo();
		
		logger.info("!!!!! HARDCODED SEPOLIA URLS HERE !!!!!!!!!!!!!!!!!!");;
		Gson gson = new Gson();
		JsonObject jsonRequest = new JsonObject();
		jsonRequest.addProperty("tokenMintTransactionId", createTokenTransactionBean.getTokenMintTransactionId());
		jsonRequest.addProperty("gameTransactionId", createTokenTransactionBean.getGameTransactionId());
		jsonRequest.addProperty("recipientAddress", "https://sepolia.etherscan.io/address/" + octi.getWeb3ContractArguments().getTo());
		jsonRequest.addProperty("transferSuccessful", octi.getTxnSuccessfull());
		jsonRequest.addProperty("ethChainId", octi.getContractChainId());
		jsonRequest.addProperty("contractAddress",octi.getContractAddress());
		jsonRequest.addProperty("transactionHash", "https://sepolia.etherscan.io/tx/" + octi.getTransactionHash());
		String jsonTokenTransferredAckn = gson.toJson(jsonRequest);
		
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("Accept", "application/json");
		headerMap.put("Content-type", "application/json");
		String gsResponseJson = httpConnectService.postRequest(headerMap, jsonTokenTransferredAckn, gameTokensTransferEventListenerUrl);
		logger.info("RESPONSE FROM GAME SERVER :: {}",gsResponseJson);
		if(gsResponseJson != null) {
			JsonObject jsonGsResponse = gson.fromJson(gsResponseJson, JsonObject.class);
		}else {
			logger.error("!!!! GAME SERVER IS DOWN !!!!");
		}
		
	}

}
