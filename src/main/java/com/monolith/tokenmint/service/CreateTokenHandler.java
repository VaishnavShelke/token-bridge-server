package com.monolith.tokenmint.service;



import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.monolith.shared.dao.EthContractInfoDAO;
import com.monolith.shared.dao.ItemInfoDAO;
import com.monolith.shared.dao.ProviderInfoDAO;
import com.monolith.shared.utils.TokenMintConstants;
import com.monolith.shared.utils.Utility;
import com.monolith.tokenmint.entities.ETHContractInfo;
import com.monolith.tokenmint.entities.GameInfo;
import com.monolith.tokenmint.entities.ItemInfoBean;
import com.monolith.tokenmint.beans.PlayerInfo;
import com.monolith.tokenmint.entities.ProviderInfo;
import com.monolith.tokenmint.beans.CreateTokenTransactionBean;
import com.monolith.tokenmint.dao.GameInfoDAO;
import com.monolith.tokenmint.dto.CreateTokenRequest;
import com.monolith.tokenmint.dto.CreateTokenResponse;

@Service
public class CreateTokenHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CreateTokenHandler.class);
	
	@Autowired
	private GameInfoDAO gameInfoDAO;
	
	@Autowired
	private ItemInfoDAO itemInfoDAO;
	
	@Autowired
	private EthContractInfoDAO ethContractInfoDAO;
	
	@Autowired
	private ProviderInfoDAO providerInfoDAO;
	
	
	@Autowired
	private Environment env;

	public CreateTokenResponse getCreateTokenResponse(CreateTokenRequest createTokenRequest, CreateTokenTransactionBean createTokenTransactionBean) {
		
		CreateTokenResponse createTokenResponse = validateTokenRequest(createTokenRequest,createTokenTransactionBean);
		if(!TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE.equals(createTokenResponse.getStatusCode())) {
			logger.error("Validation Failed !!!");
			return createTokenResponse;
		}
		
		
		populateCTTxnBeanFromCTResp(createTokenResponse,createTokenTransactionBean);
		
		logger.info("**** CreateTokenTransactionBean {}",Utility.getJsonFromObject(createTokenTransactionBean));
		return createTokenResponse;
	}

	private void populateCTTxnBeanFromCTResp(CreateTokenResponse createTokenResponse,
			CreateTokenTransactionBean createTokenTransactionBean) {
		createTokenTransactionBean.setGameTransactionId(createTokenResponse.getGameTransactionId());
	}

	private CreateTokenResponse validateTokenRequest(CreateTokenRequest createTokenRequest, CreateTokenTransactionBean createTokenTransactionBean) {
		CreateTokenResponse createTokenResponse = new CreateTokenResponse();
		createTokenResponse.setGameId(createTokenRequest.getGameId());
		createTokenResponse.setGameTransactionId(createTokenRequest.getGameTransactionId());
		createTokenResponse.setTokenMintTransactionId(createTokenTransactionBean.getTokenMintTransactionId());
		createTokenResponse.setStatusCode(TokenMintConstants.RESPONSE_CODE_SUCCESS_RESPONSE);
		createTokenResponse.setStatusDescription(TokenMintConstants.SUCCESS);
		createTokenTransactionBean.setOtp(createTokenRequest.getOtp());
		createTokenResponse.setTokenMintTransactionIdHex(createTokenTransactionBean.getTokenMintTransactionIdHex());
		createTokenTransactionBean.setGameLandingPage(createTokenRequest.getGameLandingPage());
		
		if(Utility.isNullOrEmpty(createTokenRequest.getEthContractId())
				|| Utility.isNullOrEmpty(createTokenRequest.getGameId())
				|| Utility.isNullOrEmpty(createTokenRequest.getGameTransactionId())
				|| createTokenRequest.getGameItemInfo() == null
				|| createTokenRequest.getPlayerInfo() ==null
				|| Utility.isNullOrEmpty(createTokenRequest.getOtp())) {
			logger.error("Missing Requred Field");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED,"Required Filed Missing Or Empty" );
		}
		
		String gameId = createTokenRequest.getGameId();
		
		GameInfo gameInfo = gameInfoDAO.getGameInfoByGameId(gameId);
		if(gameInfo == null) {
			logger.error("Game Info Could Not Be Found");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Game Config Could Not Be Found");
		}else {
			createTokenTransactionBean.setGameId(gameId);
			createTokenTransactionBean.setGameInfo(gameInfo);
		}
			
		PlayerInfo playerInfo = createTokenRequest.getPlayerInfo();
		if(playerInfo == null) {
			logger.error("Player Info Missing");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Please share player Info");
		}else {
			createTokenTransactionBean.setPalyerInfo(playerInfo);
		}
		
		ItemInfoBean reqItemInfoBean = createTokenRequest.getGameItemInfo();
		ItemInfoBean itemInfoBean = itemInfoDAO.getItemInfoFromItemIdForGame(gameId,reqItemInfoBean.getItemId());
		if(itemInfoBean == null) {
			logger.error("Item Info Could Not be Found");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Request Item Could not be found");
		}else {
			itemInfoBean.setItemQuantity(reqItemInfoBean.getItemQuantity());
			createTokenTransactionBean.setItemInfoBean(itemInfoBean);
		}
		
		String ethContractId = createTokenRequest.getEthContractId();
		ETHContractInfo ethContractInfo = ethContractInfoDAO.getEthContractInfoForGame(gameId,ethContractId);
		if(ethContractInfo == null) {
			logger.error("Eth Contract Info Could Not be Found");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Ethereum Contract Info Could not be found");
		}else {
			createTokenTransactionBean.setEthContractInfo(ethContractInfo);
		}
		
		String providerId = ethContractInfo.getProviderId();
		ProviderInfo providerInfo = providerInfoDAO.getProviderInfo(providerId);
		if(providerInfo == null) {
			logger.error("Eth Contract Info Could Not be Found");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Ethereum Contract Info Could not be found");
		}else {
			createTokenTransactionBean.setProviderInfo(providerInfo);
		}
		
		
		createTokenResponse.setTokenMintUrlPayload(new String(Base64.getEncoder().encode(createTokenTransactionBean.getTokenMintTransactionId().getBytes())));
	
		String loadUiUrl = env.getProperty("load.ui.url")+createTokenTransactionBean.getTokenMintTransactionId();
		String compressedLoadUiUrl = Utility.compressURL(loadUiUrl);
		StringBuilder sb = new StringBuilder();
		String redirectionUrl = env.getProperty("tokenmint.ui.page");
		
		sb.append(redirectionUrl).append("?txnId=").append(createTokenTransactionBean.getTokenMintTransactionId())	
			.append("&loadUiUrl=").append(compressedLoadUiUrl);
		createTokenResponse.setTokenMintRedirectionUrl(sb.toString());
		return createTokenResponse;
	}

}
