package com.monolith.tokenmint.service;



import java.util.Base64;

import com.monolith.tokenmint.entities.ETHContractInfoEntity;
import com.monolith.tokenmint.entities.GameInfoEntity;
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
import com.monolith.tokenmint.entities.GameItemsEntity;
import com.monolith.tokenmint.beans.PlayerInfo;
import com.monolith.tokenmint.entities.ChainProviderInfoEntity;
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
		
		GameInfoEntity gameInfoEntity = gameInfoDAO.getGameInfoByGameId(gameId);
		if(gameInfoEntity == null) {
			logger.error("Game Info Could Not Be Found");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Game Config Could Not Be Found");
		}else {
			createTokenTransactionBean.setGameId(gameId);
			createTokenTransactionBean.setGameInfoEntity(gameInfoEntity);
		}
			
		PlayerInfo playerInfo = createTokenRequest.getPlayerInfo();
		if(playerInfo == null) {
			logger.error("Player Info Missing");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Please share player Info");
		}else {
			createTokenTransactionBean.setPalyerInfo(playerInfo);
		}
		
		GameItemsEntity reqGameItemsEntity = createTokenRequest.getGameItemInfo();
		GameItemsEntity gameItemsEntity = itemInfoDAO.getItemInfoFromItemIdForGame(gameId, reqGameItemsEntity.getItemId());
		if(gameItemsEntity == null) {
			logger.error("Item Info Could Not be Found");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Request Item Could not be found");
		}else {
			gameItemsEntity.setItemQuantity(reqGameItemsEntity.getItemQuantity());
			createTokenTransactionBean.setGameItemsEntity(gameItemsEntity);
		}
		
		String ethContractId = createTokenRequest.getEthContractId();
		ETHContractInfoEntity ethContractInfoEntity = ethContractInfoDAO.getEthContractInfoForGame(gameId,ethContractId);
		if(ethContractInfoEntity == null) {
			logger.error("Eth Contract Info Could Not be Found");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Ethereum Contract Info Could not be found");
		}else {
			createTokenTransactionBean.setEthContractInfoEntity(ethContractInfoEntity);
		}
		
		String providerId = ethContractInfoEntity.getProviderId();
		ChainProviderInfoEntity chainProviderInfoEntity = providerInfoDAO.getProviderInfo(providerId);
		if(chainProviderInfoEntity == null) {
			logger.error("Eth Contract Info Could Not be Found");
			return createTokenResponse.setStatusCodeDesc(TokenMintConstants.RESPONSE_CODE_VALIDATEION_FAILED, "Ethereum Contract Info Could not be found");
		}else {
			createTokenTransactionBean.setChainProviderInfoEntity(chainProviderInfoEntity);
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
